package at.reisisoft.fsm;

import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import at.reisisoft.fsm.ui.StartPage;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("fsm")
@PreserveOnRefresh
@Title("Loading page...")
public class FsmUI extends UI {
	private Navigator navigator;

	public FsmUI() {
		navigator = new Navigator(this, this);
		View mainView = new StartPage();
		navigator.addView(Pages.DEFAULT.toString(), mainView);
		navigator.setErrorView(mainView);
	}

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = FsmUI.class)
	public static class Servlet extends VaadinServlet {
		@Override
		protected void servletInitialized() throws ServletException {
			super.servletInitialized();
			uuidXMurl = getServletContext().getRealPath("WEB-INF")
					+ File.separator + "uddi.xml";
		}

	}

	public static String uuidXMurl = null;

	@Override
	protected void init(VaadinRequest request) {

	}

	public static void setCurrentPageTitle(ViewChangeEvent e) {
		Page.getCurrent().setTitle(
				(e.getViewName().length() == 0 ? Pages.DEFAULT.toString() : e
						.getViewName()) + " - O4 FSM");

	}

}