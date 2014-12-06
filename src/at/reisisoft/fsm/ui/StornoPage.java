package at.reisisoft.fsm.ui;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import at.jku.ce.juddi.o4.UddiHelper;
import at.reisisoft.fsm.Pages;
import at.reisisoft.fsm.SqlHelper;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlLabel;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlUtils;

import com.vaadin.ui.Button;

public class StornoPage extends VerticalView {

	public StornoPage(String uuid) {
		boolean successful = doStorno(uuid);
		addComponent(new HtmlLabel(HtmlUtils.center("h1",
				"Cancelling your booking with id \"" + uuid + "\" was"
						+ (successful ? "" : " NOT") + " sucessful!")));
		Button goBack = new Button("Go to main page");
		goBack.addClickListener(event -> getUI().getNavigator().navigateTo(
				Pages.DEFAULT.toString()));
		addComponent(goBack);

	}

	private boolean doStorno(String uuid) {
		// Get Airlines
		Connection connection = SqlHelper.getConnection(
				SqlHelper.centralJdbcUrl, SqlHelper.centralUn,
				SqlHelper.centralPw);
		PreparedStatement prepStatement2 = null, prepStatement1 = null;
		ResultSet resultSet = null;
		if (connection == null) {
			return false;
		}
		List<String[]> flights = new ArrayList<>();
		try {
			prepStatement1 = connection.prepareStatement("delete from "
					+ " passengerBookingRecord where uuid_booking =?");
			prepStatement1.setString(1, uuid);
			prepStatement1.execute();
			prepStatement2 = connection
					.prepareStatement("select id_flight as flight,airline ,flight_date as fdate from "
							+ "flightBookingRecord where uuid_booking = ?");
			prepStatement2.setString(1, uuid);
			resultSet = prepStatement2.executeQuery();
			String flightID, airline;

			Date flightDate = null;
			UddiHelper helper = UddiHelper.getInstance();
			while (resultSet.next()) {
				airline = resultSet.getString("airline");
				flightID = resultSet.getString("flight");
				flightDate = resultSet.getDate("fdate");
				flights.add(new String[] { airline, flightID });
			}

			boolean working = true;
			for (String[] strings : flights) {
				working = working
						&& helper.storno(strings[0], uuid, strings[1]);
			}

			if (working) {
				connection.commit();
			} else {
				throw new SQLException("Rollback!");
			}
			for (String[] strings : flights) {
				helper.book(strings[0], uuid, strings[1], flightDate);
			}

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {

			}

		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
			if (prepStatement2 != null) {
				try {
					prepStatement2.close();
				} catch (SQLException e) {
				}
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
				}
			}
			if (prepStatement1 != null) {
				try {
					prepStatement1.close();
				} catch (SQLException e) {
				}
			}
		}

		return true;
	}

	/**
	 *
	 */
	private static final long serialVersionUID = -1348842919764872420L;

}
