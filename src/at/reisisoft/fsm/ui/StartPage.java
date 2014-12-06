package at.reisisoft.fsm.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.reisisoft.fsm.Entry;
import at.reisisoft.fsm.FsmUI;
import at.reisisoft.fsm.Pages;
import at.reisisoft.fsm.ProductData;
import at.reisisoft.fsm.SqlHelper;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlLabel;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlUtils;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;

/**
 * Landing (and error) page for this app
 * 
 * @author Florian
 *
 */
public class StartPage extends VerticalView {
	private static final String sqlFrom = "select distinct vonIATA as code,vonStadt as stadt from tmp_fsm order by stadt asc";
	private static final String sqlTo = "select distinct nachIATA as code,nachStadt as stadt from tmp_fsm order by stadt asc";

	private List<Entry> listVon = new ArrayList<>();
	private List<Entry> listNach = new ArrayList<>();

	@Override
	public void enter(ViewChangeEvent event) {
		FsmUI.setCurrentPageTitle(Pages.DEFAULT.toString());
	}

	public StartPage() {
		Connection connection = SqlHelper.getConnection();
		ResultSet rsVon = null, rsNach = null;
		if (connection != null) {
			Statement statement = null;
			try {
				statement = connection.createStatement();
				rsVon = statement.executeQuery(sqlFrom);
				fillList(rsVon, listVon);
				rsNach = statement.executeQuery(sqlTo);
				fillList(rsNach, listNach);

			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {

				}
			} finally {
				try {
					if (statement != null && !statement.isClosed()) {
						statement.close();
					}
				} catch (SQLException e) {
				}
				try {
					if (rsNach != null && !rsNach.isClosed()) {
						rsNach.close();
					}
				} catch (SQLException e) {
				}
				try {
					if (rsVon != null && !rsVon.isClosed()) {
						rsVon.close();
					}
				} catch (SQLException e) {
				}
				try {
					connection.close();
				} catch (SQLException e1) {
				}
			}
		}

		// Create layout
		setMargin(true);
		addComponent(new HtmlLabel(HtmlUtils.center("<h1>"
				+ ProductData.getInstance().getProduct()
				+ "<h1><p><b>Welcome!</b>")));
		addComponent(new HtmlLabel(HtmlUtils.center("h1",
				"Search for the cheapest flight. Start NOW!")));
		ComboBox von = new ComboBox("From:"), nach = new ComboBox("To:");
		DateField dateField = new PopupDateField("Departure date:");
		dateField.setDateFormat("dd.MM.yyyy");
		dateField.setValue(new Date(System.currentTimeMillis()));
		von.addItems(listVon);
		nach.addItems(listNach);
		GridLayout gridLayout = new GridLayout(3, 1);
		gridLayout.setSizeFull();

		gridLayout.addComponent(von, 0, 0);
		gridLayout.addComponent(nach, 1, 0);
		gridLayout.addComponent(dateField, 2, 0);
		addComponent(gridLayout);
		Button cont = new Button("Save NOW (Search)");
		cont.addClickListener(event -> {
			Date bookingDate = dateField.getValue();
			Navigator navigator = getUI().getNavigator();
			Entry ev = (Entry) von.getValue();
			Entry en = (Entry) nach.getValue();
			if (ev != null && en != null && !ev.equals(en)) {
				View resultPage = new ResultPage(ev.key, ev.value, en.key,
						en.value, bookingDate);
				navigator.addView(Pages.QUERY.toString(), resultPage);
				navigator.navigateTo(Pages.QUERY.toString());
			}
		});
		addComponent(cont);
		GridLayout gridLayout2 = new GridLayout(2, 1);
		Button storno = new Button("Storno");
		TextField uuidField = new TextField("Booking code");
		gridLayout2.addComponent(uuidField, 0, 0);
		gridLayout2.addComponent(storno, 1, 0);
		storno.addClickListener(event -> {
			boolean validUUID = uuidField.getValue().length() > 1;
			if (validUUID) {
				Connection c = SqlHelper.getConnection(
						SqlHelper.centralJdbcUrl, SqlHelper.centralUn,
						SqlHelper.centralPw);
				PreparedStatement statement = null;
				ResultSet resultSet = null;
				if (connection != null) {
					try {
						statement = c
								.prepareStatement("select uuid_booking from passengerBookingRecord"
										+ "where uuid_booking = ? AND flightSearchEngine =?");
						statement.setString(1, uuidField.getValue());
						statement.setString(2, ProductData.getInstance()
								.getProduct());
						resultSet = statement.executeQuery();
						validUUID = resultSet.next() && !resultSet.next(); // Exactly
						// one
						// entry
						View cancelConfirm = new ConfirmStornoPage(uuidField
								.getValue());
						Navigator navigator = getUI().getNavigator();
						navigator.addView(Pages.CONFIRM_STORNO.toString(),
								cancelConfirm);
						navigator.navigateTo(Pages.CONFIRM_STORNO.toString());
					} catch (SQLException e) {
						validUUID = false;
						try {
							c.rollback();
						} catch (Exception e1) {
						}
					} finally {
						try {
							c.close();
						} catch (Exception e) {

						}
						if (statement != null) {
							try {
								statement.close();
							} catch (Exception e) {

							}
						}
						if (resultSet != null) {
							try {
								resultSet.close();
							} catch (Exception e) {

							}
						}
					}
				}
				Navigator navigator = getUI().getNavigator();
				View view = new ConfirmStornoPage(uuidField.getValue());
				navigator.addView(Pages.CONFIRM_STORNO.toString(), view);
				navigator.navigateTo(Pages.CONFIRM_STORNO.toString());
			}
		});
		addComponent(gridLayout2);
	}

	private void fillList(ResultSet rs, List<Entry> list) throws SQLException {
		String code, stadt;
		while (rs.next()) {
			code = rs.getString("code");
			stadt = rs.getString("stadt");
			list.add(new Entry(code, stadt));
		}
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 2777250725706573656L;

}
