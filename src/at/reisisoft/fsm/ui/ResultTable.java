package at.reisisoft.fsm.ui;

import java.math.BigDecimal;
import java.util.Date;

import at.reisisoft.fsm.Entry;
import at.reisisoft.fsm.Pages;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;

/**
 * Specialisation of {@link com.vaadin.ui.Table} made for {@link ResultPage}
 *
 * @author Florian
 */
public class ResultTable extends Table {

	private static final long serialVersionUID = -5226603766660338775L;
	private static final String von = "From", ueber = "Over", nach = "NTo",
			airline1 = "Involved Airlines (1)",
			airline2 = "Involved Airlines (2)", preis = "Price",
			umstiege = "Transfers", dauer = "Duration", abflug = "Departure",
			ankunft = "Arrival", buchen = " ";
	private Date date;

	public ResultTable(Date bookingDate) {
		date = bookingDate;
		addContainerProperty(von, String.class, "---");
		addContainerProperty(ueber, String.class, "---");
		addContainerProperty(nach, String.class, "---");
		addContainerProperty(airline1, String.class, "---");
		addContainerProperty(airline2, String.class, "---");
		addContainerProperty(umstiege, Integer.class, -1);
		addContainerProperty(dauer, String.class, "??:??");
		addContainerProperty(abflug, String.class, "??:??");
		addContainerProperty(ankunft, String.class, "??:??");
		addContainerProperty(preis, BigDecimal.class, null);
		addContainerProperty(buchen, Button.class, null);
		setSizeFull();
	}

	public void addItem(String von, String ueber, String nach,
			final String airline1, final String airline2, final int umstiege,
			long dauer, long abflug, long ankunft, final String fID1,
			final String fID2, BigDecimal preis) {
		Object[] row = new Object[11];
		row[0] = von;
		row[1] = ueber;
		row[2] = nach;
		row[3] = airline1;
		row[4] = airline2;
		row[5] = umstiege;
		row[6] = toTimeStamp(dauer);
		row[7] = toTimeStamp(abflug);
		row[8] = toTimeStamp(ankunft);
		Button button = new Button("Book now!");
		row[9] = preis;
		row[10] = button;

		button.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				Entry[] entries = new Entry[umstiege + 1];
				entries[0] = new Entry(fID1, airline1);
				if (entries.length == 2) {
					entries[1] = new Entry(fID2,
							airline2.equals("---") ? airline1 : airline2);
				}
				Navigator navigator = getUI().getNavigator();
				View view = new ConfirmBookingPage(entries, navigator, date);
				navigator.addView(Pages.CONFIRM_BOOKING.toString(), view);
				navigator.navigateTo(Pages.CONFIRM_BOOKING.toString());

			}
		});
		// Add to table
		addItem(row, null);

	}

	private String toTimeStamp(long time) {
		return time / 100 + ":" + time % 100;
	}
}
