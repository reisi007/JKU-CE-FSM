package at.reisisoft.fsm.ui;

import at.reisisoft.fsm.Pages;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlLabel;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlUtils;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
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

	public ConfirmStornoPage(final String uuid) {
		GridLayout gridLayout = new GridLayout(2, 1);
		addComponent(new HtmlLabel(HtmlUtils.center("h1",
				"Cancel booking with ID \"" + uuid + "\"  (cannot be undone)")));
		Button yes = new Button("Yes, storno");
		Button no = new Button("No, go to main page");
		gridLayout.addComponent(yes, 0, 0);
		gridLayout.addComponent(no, 1, 0);
		addComponent(gridLayout);
		no.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(Pages.DEFAULT.toString());

			}
		});
		yes.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				Navigator navigator = getUI().getNavigator();
				View view = new StornoPage(uuid);
				navigator.addView(Pages.STORNO.toString(), view);
				navigator.navigateTo(Pages.STORNO.toString());

			}
		});
	}
}
