package at.reisisoft.fsm.ui;

import at.reisisoft.fsm.FsmUI;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

public abstract class VerticalView extends VerticalLayout implements View {

	@Override
	public final void enter(ViewChangeEvent event) {
		FsmUI.setCurrentPageTitle(event);
	}

}
