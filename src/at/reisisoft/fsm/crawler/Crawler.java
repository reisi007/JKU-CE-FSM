package at.reisisoft.fsm.crawler;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import at.jku.ce.airline.service.AirlineServiceImpl;
import at.jku.ce.airline.service.AirlineServiceImplService;
import at.jku.ce.airline.service.Flight;
import at.jku.ce.juddi.UddiManager;
import at.jku.ce.juddi.o4.UddiHelper;
import at.reisisoft.fsm.SqlHelper;

/**
 * A small program which pulls all available flights into a DB
 *
 * @author Florian
 *
 */
public class Crawler implements Runnable {
	private static long waitTime = 1000 * 60 * 60; // 1 h
	private final String xmlUrl;

	public Crawler(String uddiXmlUrl) {
		xmlUrl = uddiXmlUrl;
	}

	@Override
	public void run() {
		try {
			// Prepared statements
			String SqlTruncateTable = "truncate tmp_fsm";
			String SqlInsertInto = "INSERT INTO tmp_fsm (flugnr,airline,vonIATA,nachIATA,dayofweek,vonStadt,nachStadt,t_abflug,t_ankunft,preis) VALUES (?,?,?,?,?,?,?,?,?,?)";
			Connection connection = null;
			PreparedStatement instertStatement = null;
			Statement truncateStatement = null, s = null;
			HashMap<String, String> flightToAirline = new HashMap<>();
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.err.println("MySQL driver not found!!");
				return;
			}
			try {

				connection = SqlHelper.getConnection();
				if (connection == null) {
					return;
				}
				System.out.println("Connected to SQL server");

				try {
					instertStatement = connection
							.prepareStatement(SqlInsertInto);
					truncateStatement = connection.createStatement();
				} catch (SQLException e) {
					System.err.println("MySQL exception:" + e.getMessage()
							+ " Error code:" + e.getErrorCode());
					return;
				}
				String airlineName = null;
				List<String> endpoints = null;
				List<Flight> airlineFlights = null;
				List<Flight> flights = new ArrayList<>();
				UddiManager jkumanager = UddiHelper.getInstance().getManager(
						xmlUrl);
				// Try create fake instance
				try {
					jkumanager.publish("FAIL", "FAILAIR",
							"http://example.com/fail");
				} catch (Exception e) {
				}
				System.out.println("Starting the crawler!\n\n");
				while (true) {
					System.out.println("Starting fetching flights");

					try {
						// Connect to DB
						endpoints = jkumanager.getAllPublishedAccessPoints();
						System.out.println("Found " + endpoints.size()
								+ " endpoints. Creating services");
						for (int i = 0; i < endpoints.size(); i++) {
							try {
								AirlineServiceImpl airline = new AirlineServiceImplService(
										new URL(endpoints.get(i)))
										.getAirlineServiceImplPort();
								airlineName = airline.getAirline().getName();
								airlineFlights = airline.getFlightplan();
								for (Flight f : airlineFlights) {
									flightToAirline.put(f.getFlightId(),
											airlineName);
								}
								flights.addAll(airlineFlights);
							} catch (Exception e) {
								System.err.println("\nCannot connect to"
										+ endpoints.get(i) + "\n");
							}
						}
						System.out.println("Found " + flights.size()
								+ " flights");
						truncateStatement.execute(SqlTruncateTable);
						// Insert into DB
						for (Flight f : flights) {
							String stmp = null;
							long ltmp;
							int itmp;
							try {
								stmp = f.getFlightId();
								if (stmp.length() > 50) {
									throw new ArgumentException(
											"FLUGNR Too long, (50) -> ("
													+ stmp.length() + ')');
								}
								instertStatement.setString(1, stmp);

								stmp = flightToAirline.get(f.getFlightId());
								if (stmp.length() > 50) {
									throw new ArgumentException(
											"AIRLINE Too long, (50) -> ("
													+ stmp.length() + ')');
								}
								instertStatement.setString(2, stmp);

								stmp = f.getDepartesFrom().getIcao();
								if (stmp.length() > 4) {
									throw new ArgumentException(
											"VONIATA Too long, (4) -> ("
													+ stmp.length() + ')');
								}
								instertStatement.setString(3, stmp);

								stmp = f.getArrivesAt().getIcao();
								if (stmp.length() > 4) {
									throw new ArgumentException(
											"NACHIATA Too long, (4) -> ("
													+ stmp.length() + ')');
								}
								instertStatement.setString(4, stmp);

								itmp = f.getDepartureTime().getIndexDayOfWeek();
								if (itmp < 1 || itmp > 7) {
									throw new ArgumentException(
											"Daysofweek Not in range, [1-7]-> ("
													+ itmp + ')');
								}
								instertStatement.setInt(5, itmp);

								stmp = f.getDepartesFrom().getCity();
								if (stmp.length() > 50) {
									throw new ArgumentException(
											"vonStadt Too long, (50) -> ("
													+ stmp.length() + ')');
								}
								instertStatement.setString(6, stmp);

								stmp = f.getArrivesAt().getCity();
								if (stmp.length() > 50) {
									throw new ArgumentException(
											"nachStadt Too long, (50) -> ("
													+ stmp.length() + ')');
								}
								instertStatement.setString(7, stmp);

								ltmp = f.getDepartureTime().getTimeOfDay();
								if (ltmp < 0 || ltmp > 2400) {
									throw new ArgumentException(
											"t_abflug Not in range, [0000-2400]-> ("
													+ ltmp + ')');
								}
								instertStatement.setInt(8, (int) ltmp);

								ltmp = f.getArrivalTime().getTimeOfDay();
								if (ltmp < 0 || ltmp > 2400) {
									throw new ArgumentException(
											"t_ankunft Not in range, [0000-2400]-> ("
													+ ltmp + ')');
								}

								instertStatement.setInt(9, (int) ltmp);

								instertStatement.setBigDecimal(
										10,
										f.getStdFee()
												.add(f.getDepartesFrom()
														.getAirportTax())
												.add(f.getArrivesAt()
														.getAirportTax()));
								// System.out.println(instertStatement.toString());
								// System.out.println(instertStatement.executeUpdate());
								instertStatement.addBatch();
								System.out.println("Wrote flight with id"
										+ f.getFlightId());
							} catch (ArgumentException | NullPointerException e) {
								System.out.println(e);
							} finally {
								instertStatement.clearParameters();

							}
						}
						instertStatement.executeBatch();
						connection.commit();
						System.out.println("COMMIT SUCCESSFUL");

						System.out.println("Waiting" + waitTime / 60000f
								+ "min");
						System.out.println(new Time(System.currentTimeMillis())
								.toString());
						Thread.sleep(waitTime);

					} catch (SQLException sqle) {
						System.out.println(sqle.getMessage() + "  Code:"
								+ sqle.getErrorCode());
					} finally {
						flights.clear();
						flightToAirline.clear();
					}
				}
			} catch (Exception e) {
				System.err.println("An exception occured, message:\n"
						+ e.getMessage());
			} finally {

				try {
					instertStatement.close();
					truncateStatement.close();
				} catch (SQLException e) {
					try {
						connection.rollback();
					} catch (SQLException e1) {
					}
				}
			}

		} catch (Error error) {

		}
	}

}
