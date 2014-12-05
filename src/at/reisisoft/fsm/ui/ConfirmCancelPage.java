package at.reisisoft.fsm.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;

public class ConfirmCancelPage extends VerticalView {

	/**
	 *
	 */
	private static final long serialVersionUID = -6223322644335494504L;

	public ConfirmCancelPage(String uuid) {
		GridLayout gridLayout = new GridLayout(2, 1);
		Button yes = new Button(
				"Ja, stornieren (kann nicht Rückgängig gemacht werden)");
		Button no = new Button("Nein, zurück zur Hautseite");
		gridLayout.addComponent(yes, 0, 0);
		gridLayout.addComponent(no, 1, 0);
		addComponent(gridLayout);
	}

}
