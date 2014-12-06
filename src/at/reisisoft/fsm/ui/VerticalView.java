package at.reisisoft.fsm.ui;

import at.reisisoft.fsm.FsmUI;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

/**
 * A helper basis class, which implements changing the title of the page.
 * {@link at.reisisoft.fsm.Pages} and {@link #enter(ViewChangeEvent)}
 *
 * @author Florian
 *
 */
public abstract class VerticalView extends VerticalLayout implements View {

	@Override
	public void enter(ViewChangeEvent event) {
		FsmUI.setCurrentPageTitle(event.getViewName());
	}

}
