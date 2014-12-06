package at.reisisoft.fsm.ui;

import at.reisisoft.fsm.Pages;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlLabel;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlUtils;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;

/**
 * Asks the user for confirmation. If he wants to storno, he is redicted to
 * {@link StornoPage}
 *
 * @author Florian
 *
 */
public class ConfirmStornoPage extends VerticalView {

	/**
	 *
	 */
	private static final long serialVersionUID = -6223322644335494504L;

	public ConfirmStornoPage(String uuid) {
		GridLayout gridLayout = new GridLayout(2, 1);
		addComponent(new HtmlLabel(
				HtmlUtils
				.center("h1",
						"Buchung mit Buchungsnummer \""
								+ uuid
								+ "\" stornieren (kann nicht Rückgängig gemacht werden")));
		Button yes = new Button("Ja, stornieren");
		Button no = new Button("Nein, zurück zur Hautseite");
		gridLayout.addComponent(yes, 0, 0);
		gridLayout.addComponent(no, 1, 0);
		addComponent(gridLayout);
		no.addClickListener(e -> {
			getUI().getNavigator().navigateTo(Pages.DEFAULT.toString());
		});
		yes.addClickListener(e -> {
			Navigator navigator = getUI().getNavigator();
			View view = new StornoPage(uuid);
			navigator.addView(Pages.STORNO.toString(), view);
			navigator.navigateTo(Pages.STORNO.toString());
		});
	}
}
