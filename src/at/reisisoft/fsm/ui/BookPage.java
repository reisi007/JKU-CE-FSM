package at.reisisoft.fsm.ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import at.jku.ce.airline.service.AirlineServiceImpl;
import at.jku.ce.juddi.UddiManager;
import at.reisisoft.fsm.Entry;
import at.reisisoft.fsm.FsmUI;
import at.reisisoft.fsm.PersonalInformation;
import at.reisisoft.fsm.ProductData;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlLabel;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlUtils;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

public class BookPage extends VerticalLayout implements View {

	public BookPage(Entry[] tobook, PersonalInformation pi, Date date) {
		assert tobook.length > 0 && pi.isValid();
		boolean bookingValid = doBook(tobook, date, pi);
		if (bookingValid) {
			addComponent(new HtmlLabel(HtmlUtils.center("h1",
					"Successfully booked")));
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

	@Override
	public void enter(ViewChangeEvent event) {
		FsmUI.setCurrentPageTitle(event);
	}

	// flightId - airline
	private boolean doBook(Entry[] tobook, Date date, PersonalInformation pi) {
		try {
			String uuid = null;
			Connection connection = null;
			PreparedStatement statement = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(
						"jdbc:mysql://140.78.73.67:3306/airlineDB", "ceue",
						"ceair14db");
				connection.setAutoCommit(false);
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (SQLException e2) {
				System.out.println(e2.getMessage() + " Error: "
						+ e2.getErrorCode());
			}
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
					statement
							.setString(5, ProductData.getInstance().toString());
				} catch (SQLException e) {
					uuid = null;
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
			}
			if (uuid == null) {
				return false;
			}
			assert uuid != null;
			UddiManager uddiManager = UddiManager.getInstance();
			for (int i = 0; i < tobook.length; i++) {
				AirlineServiceImpl airline = forAirline(tobook[i].value);
				GregorianCalendar gregory = new GregorianCalendar();
				gregory.setTime(date);
				XMLGregorianCalendar calendar = DatatypeFactory.newInstance()
						.newXMLGregorianCalendar(gregory);
				airline.bookFlight(uuid, tobook[i].key, calendar);
			}

		} catch (Exception e) {
			return false;
		}
		return true;

	}

	private AirlineServiceImpl forAirline(String name) {
		return null; // TODO Actual implementation

	}
}
