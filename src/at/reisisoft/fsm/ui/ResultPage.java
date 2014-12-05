package at.reisisoft.fsm.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;

import at.reisisoft.fsm.SqlHelper;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlLabel;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlUtils;

public class ResultPage extends VerticalView {
	private String vonCode, vonStadt, zuCode, zuStadt;
	private DayOfWeek dow;
	private Date bookingDate;
	private final String sql = "select distinct * from"
			+ "(select a.preis+b.preis +10 * IF(a.airline = b.airline,1,2)as preis, a.flugnr as flugID1, b.flugnr as flugID2, a.vonIATA as von, "
			+ "b.nachIATA as nach,a.nachIATA as ueber, a.airline as airline1, b.airline as airline2,1 as umstiege,"
			+ " (floor(b.t_ankunft/100)-(floor(a.t_abflug/100)))*100+b.t_ankunft%100-(60-a.t_abflug%100) as dauer,"
			+ " a.t_abflug as abflug, b.t_ankunft as ankunft  from tmp_fsm a, tmp_fsm b "
			+ "where a.nachIATA = b.vonIATA AND a.dayOfweek = b.dayOfweek AND a.t_ankunft <= b.t_abflug "
			+ "AND a.dayOfweek = ? AND a.vonIATA = ? AND b.nachIATA = ?) a "
			+ "union "
			+ "(select preis+10 as preis,flugnr as flugID1, '---' as flugID2, vonIATA as von, nachIATA as nach, '---' as ueber,airline as airline1, '---' as airline2, "
			+ "0 as umstiege, (floor(t_ankunft/100)-(floor(t_abflug/100)))*100+t_ankunft%100-(60-t_abflug%100) as dauer, t_abflug as abflug, t_ankunft as ankunft "
			+ "from tmp_fsm where vonIATA = ? AND nachIATA = ? AND dayOfweek = ?)";

	public ResultPage(String vonCode, String vonStadt, String zuCode,
			String zuStadt, Date bookingDate) {
		this.vonCode = vonCode;
		this.vonStadt = vonStadt;
		this.zuCode = zuCode;
		this.zuStadt = zuStadt;
		this.bookingDate = bookingDate;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(bookingDate);
		dow = DayOfWeek.of(calendar.get(Calendar.DAY_OF_WEEK));
		ResultTable table = new ResultTable(bookingDate);

		// Connect to DB
		Connection connection = SqlHelper.getConnection();
		if (connection != null) {
			PreparedStatement statement = null;
			ResultSet resultSet = null;
			try {
				statement = connection.prepareStatement(sql);
				// Set dow
				statement.setInt(1, dow.getValue());
				statement.setInt(6, dow.getValue());
				// Set vonIATA
				statement.setString(2, vonCode);
				statement.setString(4, vonCode);
				// Set nachIATA
				statement.setString(3, zuCode);
				statement.setString(5, zuCode);
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
								.format(bookingDate) + " from" + vonCode + " ("
						+ vonStadt + ") to " + zuCode + " (" + zuStadt + ")")));

		addComponent(table);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 8074642419954002311L;

}
