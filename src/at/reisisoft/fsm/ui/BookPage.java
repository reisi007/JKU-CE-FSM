package at.reisisoft.fsm.ui;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import at.jku.ce.airline.service.AirlineServiceImpl;
import at.jku.ce.airline.service.AirlineServiceImplService;
import at.jku.ce.juddi.UddiManager;
import at.reisisoft.fsm.Entry;
import at.reisisoft.fsm.PersonalInformation;
import at.reisisoft.fsm.ProductData;
import at.reisisoft.fsm.SqlHelper;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlLabel;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlUtils;

public class BookPage extends VerticalView {

	public BookPage(Entry[] tobook, PersonalInformation pi, Date date) {
		assert tobook.length > 0 && pi.isValid();
		String bookingCode = doBook(tobook, date, pi);
		if (bookingCode != null) {
			addComponent(new HtmlLabel(HtmlUtils.center("h1",
					"Successfully booked. Please keep the following code:")));
			addComponent(new HtmlLabel(HtmlUtils.center("h1", bookingCode)));
		} else {
			addComponent(new HtmlLabel(HtmlUtils.center("h1",
					"Unable to book your flights!")));
		}
		addComponent(new HtmlLabel(HtmlUtils.center("Affected flights:")));
		for (Entry e : tobook) {
			addComponent(new HtmlLabel(
					HtmlUtils.center(e.value + " - " + e.key)));
		}

	}

	private String errorMessage = null;
	/**
	 *
	 */
	private static final long serialVersionUID = 5918957729367643044L;

	// flightId - airline
	/**
	 *
	 * @return NULL if unsucessfull, an unique ID if successfull
	 */
	private String doBook(Entry[] tobook, Date date, PersonalInformation pi) {
		try {
			String uuid = null;
			Connection connection = SqlHelper.getConnection(
					SqlHelper.centralJdbcUrl, SqlHelper.centralUn,
					SqlHelper.centralPw);
			PreparedStatement statement = null;
			if (connection != null) {
				try {
					statement = connection
							.prepareStatement("INSERT INTO passengerBookingRecord "
									+ "(uuid_booking,firstname,lastname,idcardNr,flightSearchEngine) "
									+ "VALUES(?,?,?,?,?)");
					uuid = UUID.randomUUID().toString();
					statement.setString(1, uuid);
					statement.setString(2, pi.getFirstName());
					statement.setString(3, pi.getLastname());
					statement.setString(4, pi.getIdcard());
					statement.setString(5, ProductData.getInstance()
							.getProduct());
					statement.execute();
					connection.commit();
				} catch (SQLException e) {
					try {
						connection.rollback();
					} catch (SQLException ignore) {

					}
				} finally {
					if (connection != null) {
						try {
							connection.close();
						} catch (SQLException ignore) {

						}
					}
					if (statement != null) {
						try {
							statement.close();
						} catch (SQLException ignore) {

						}
					}
				}
				if (uuid != null) {
					boolean working = true;
					for (int i = 0; i < tobook.length; i++) {
						AirlineServiceImpl airline = forAirline(tobook[i].value);
						GregorianCalendar gregory = new GregorianCalendar();
						gregory.setTime(date);
						XMLGregorianCalendar calendar = DatatypeFactory
								.newInstance().newXMLGregorianCalendar(gregory);
						working = working
								&& airline.bookFlight(uuid, tobook[i].key,
										calendar);
					}
					if (!working) {
						Connection c = SqlHelper.getConnection(
								SqlHelper.centralJdbcUrl, SqlHelper.centralUn,
								SqlHelper.centralPw);
						PreparedStatement statement2 = null;
						if (c != null) {
							try {
								statement2 = c
										.prepareStatement("delete from passengerBookingRecord where uuid_booking = ?");
								statement2.setString(1, uuid);
								statement2.execute();
							} catch (SQLException e) {
								try {
									c.rollback();
								} catch (SQLException ignore) {

								}
							} finally {
								if (c != null) {
									try {
										c.close();
									} catch (SQLException ignore) {

									}
								}
								if (statement2 != null) {
									try {
										statement2.close();
									} catch (SQLException ignore) {

									}
								}
							}
						}

						uuid = null;
					}
				}
			}
			return uuid;
			// NPE if airline is not online
		} catch (Exception e) {
			return null; //
		}
	}

	private AirlineServiceImpl forAirline(String name) {
		UddiManager uddiManager = UddiManager.getInstance();
		for (String s : uddiManager.getAllPublishedAccessPoints()) {
			AirlineServiceImpl airline = getAirlineServiceImple(s);
			if (name.equals(airline.getAirline().getName())) {
				return airline;
			}
		}
		return null;
	}

	private AirlineServiceImpl getAirlineServiceImple(String url) {
		try {
			return new AirlineServiceImplService(new URL(url))
			.getAirlineServiceImplPort();
		} catch (Exception e) {
			return null;
		}

	}
}
