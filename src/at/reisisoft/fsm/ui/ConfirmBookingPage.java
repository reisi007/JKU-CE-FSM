package at.reisisoft.fsm.ui;

import java.util.Date;

import at.reisisoft.fsm.Entry;
import at.reisisoft.fsm.Pages;
import at.reisisoft.fsm.PersonalInformation;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlLabel;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlUtils;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;

/**
 * Asks the user for confirmation. If he wants to book, he is redicted to
 * {@link BookPage}
 *
 * @author Florian
 *
 */
public class ConfirmBookingPage extends VerticalView {
	public ConfirmBookingPage(final Entry[] tobook, final Navigator navigator,
			final Date date) {

		StringBuilder sb = new StringBuilder("Do you really want to book:<p>");
		for (Entry e : tobook) {
			sb.append(e.value).append('-').append(e.key);
		}
		addComponent(new HtmlLabel(HtmlUtils.center("h1",
				"Do you really want to book")));
		GridLayout gridLayout = new GridLayout(2, 1);

		final TextField fn = new TextField("First name:");
		final TextField ln = new TextField("Last name:");
		final TextField id = new TextField("ID card (Passport number):");
		addComponent(fn);
		addComponent(ln);
		addComponent(id);
		addComponent(gridLayout);
		Button yes = new Button("Yes, order at owner's expense");
		Button no = new Button("No, back to the overview");
		gridLayout.addComponent(yes, 0, 0);
		gridLayout.addComponent(no, 1, 0);
		yes.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				PersonalInformation pi = new PersonalInformation(fn.getValue(),
						ln.getValue(), id.getValue());
				if (pi.isValid()) {
					View bookingView = new BookPage(tobook, pi, date);
					navigator.addView(Pages.BOOK.toString(), bookingView);
					navigator.navigateTo(Pages.BOOK.toString());
				}

			}
		});
		no.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(Pages.QUERY.toString());

			}
		});
		addComponent(new HtmlLabel(
				HtmlUtils
				.center("This booking might last some minutes, please stay at this page until you get passed to an other page!")));

	}

	/**
	 *
	 */
	private static final long serialVersionUID = -5166104044782165085L;

}
