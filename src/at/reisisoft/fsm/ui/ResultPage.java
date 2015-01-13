package at.reisisoft.fsm.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import at.reisisoft.fsm.SqlHelper;
import at.reisisoft.fsm.ui.util.DayOfWeek;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlLabel;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlUtils;

/**
 * Searches for and displays the result of a flight query
 *
 * @author Florian
 *
 */
public class ResultPage extends VerticalView {
	private DayOfWeek dow;
	private final static int serviceTax = 10;
	private final String sql = "SELECT "
			+ "preis, "
			+ "flugID1, "
			+ "flugID2,"
			+ " von, "
			+ "nach, "
			+ "ueber, "
			+ "airline1, "
			+ "airline2, "
			+ "umstiege, "
			+ "abflug, "
			+ "ankunft, "
			+ " (floor(ankunft/100)-floor(abflug/100))*100 +( 60- abflug % 100 + ankunft % 100) %60 AS dauer "
			+ "FROM " + "(SELECT  " + "preis, " + "flugID1, " + "flugID2, "
			+ "von, " + "nach, " + "ueber, " + " airline1, " + "airline2, "
			+ "umstiege, " + "abflug, " + "ankunft " + "FROM " + "(SELECT  "
			+ "a.preis + b.preis + "
			+ serviceTax
			+ " * IF(a.airline = b.airline, 1, 2) AS preis, "
			+ " a.flugnr AS flugID1, "
			+ "b.flugnr AS flugID2, "
			+ "a.vonIATA AS von, "
			+ "b.nachIATA AS nach, "
			+ "a.nachIATA AS ueber, "
			+ "a.airline AS airline1, "
			+ "b.airline AS airline2, "
			+ "1 AS umstiege, "
			+ "a.t_abflug AS abflug, "
			+ "b.t_ankunft AS ankunft "
			+ "FROM "
			+ "tmp_fsm a, tmp_fsm b "
			+ "WHERE "
			+ "a.nachIATA = b.vonIATA "
			+ " AND a.dayOfweek = b.dayOfweek "
			+ "AND a.t_ankunft <= b.t_abflug "
			+ "AND b.t_abflug <= a.t_ankunft + 600 "
			+ "AND a.dayOfweek = ? "
			+ "AND a.vonIATA = ? "
			+ "AND b.nachIATA = ?) via UNION (SELECT  "
			+ "preis + "
			+ serviceTax
			+ " AS preis, "
			+ "flugnr AS flugID1, "
			+ "'---' AS flugID2, "
			+ "vonIATA AS von, "
			+ "nachIATA AS nach, "
			+ "'---' AS ueber, "
			+ "airline AS airline1, "
			+ "'---' AS airline2, "
			+ "0 AS umstiege, "
			+ "t_abflug AS abflug, "
			+ "t_ankunft AS ankunft "
			+ "FROM "
			+ "tmp_fsm " + "WHERE " + " dayOfweek = ? AND vonIATA = ?  " // WHERE
			+ "AND nachIATA = ?)) a";

	public ResultPage(String vonCode, String vonStadt, String zuCode,
			String zuStadt, Date bookingDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(bookingDate);
		dow = DayOfWeek.fromCalendar(calendar);
		ResultTable table = new ResultTable(bookingDate);

		// Connect to DB
		Connection connection = SqlHelper.getConnection();
		if (connection != null) {
			PreparedStatement statement = null;
			ResultSet resultSet = null;
			try {
				statement = connection.prepareStatement(sql);
				// Set dow
				statement.setInt(1, dow.getDay());
				statement.setInt(4, dow.getDay());
				// Set vonIATA
				statement.setString(2, vonCode);
				statement.setString(5, vonCode);
				// Set nachIATA
				statement.setString(3, zuCode);
				statement.setString(6, zuCode);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					table.addItem(resultSet.getString("von"),
							resultSet.getString("ueber"),
							resultSet.getString("nach"),
							resultSet.getString("airline1"),
							resultSet.getString("airline2"),
							resultSet.getInt("umstiege"),
							resultSet.getLong("dauer"),
							resultSet.getLong("abflug"),
							resultSet.getLong("ankunft"),
							resultSet.getString("flugID1"),
							resultSet.getString("flugID2"),
							resultSet.getBigDecimal("preis"));
				}
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {

				}
			} finally {
				try {
					if (statement != null) {
						statement.close();
					}
				} catch (SQLException e) {
				}
				try {
					if (resultSet != null) {
						resultSet.close();
					}
				} catch (SQLException e) {
				}
				try {
					connection.close();
				} catch (SQLException e1) {
				}
			}
		}

		addComponent(new HtmlLabel(HtmlUtils.center(
				"h1",
				"Flights at "
						+ dow.toString()
						+ ", "
						+ new SimpleDateFormat("dd.MM.yyyy")
				.format(bookingDate) + " from " + vonCode
				+ " (" + vonStadt + ") to " + zuCode + " (" + zuStadt
				+ ")")));

		addComponent(table);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 8074642419954002311L;

}
