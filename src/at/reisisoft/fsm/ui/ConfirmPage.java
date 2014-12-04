package at.reisisoft.fsm.ui;

import java.util.Date;

import at.reisisoft.fsm.Entry;
import at.reisisoft.fsm.FsmUI;
import at.reisisoft.fsm.Pages;
import at.reisisoft.fsm.PersonalInformation;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlLabel;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlUtils;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ConfirmPage extends VerticalLayout implements View {
	public ConfirmPage(Entry[] tobook, Navigator navigator, Date date) {

		StringBuilder sb = new StringBuilder("Do you really want to book:<p>");
		for (Entry e : tobook) {
			sb.append(e.value).append('-').append(e.key);
		}
		addComponent(new HtmlLabel(HtmlUtils.center("h1",
				"Wollen Sie wirklich buchen?")));
		GridLayout gridLayout = new GridLayout(2, 1);

		TextField fn = new TextField("First name:");
		TextField ln = new TextField("Last name:");
		TextField id = new TextField("ID card (Passport number):");
		addComponent(fn);
		addComponent(ln);
		addComponent(id);
		addComponent(gridLayout);
		Button yes = new Button("Ja, kostenpflichtig bestellen");
		Button no = new Button("Nein, zurück zur Übersicht");
		gridLayout.addComponent(yes, 0, 0);
		gridLayout.addComponent(no, 1, 0);
		yes.addClickListener(event -> {
			PersonalInformation pi = new PersonalInformation(fn.getValue(), ln
					.getValue(), id.getValue());
			if (pi.isValid()) {
				View bookingView = new BookPage(tobook, pi, date);
				navigator.addView(Pages.BOOK.toString(), bookingView);
				navigator.navigateTo(Pages.BOOK.toString());
			}
		});
		no.addClickListener(event -> {
			navigator.navigateTo(Pages.QUERY.toString());
		});
		addComponent(new HtmlLabel(
				HtmlUtils
						.center("Die Buchung könnte etwas Dauern, bitte bleiben Sie auf der Seite, bis sie weiter geleitet werden!")));

	}

	/**
	 *
	 */
	private static final long serialVersionUID = -5166104044782165085L;

	@Override
	public void enter(ViewChangeEvent event) {
		FsmUI.setCurrentPageTitle(event);
	}

}
