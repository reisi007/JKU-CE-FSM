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
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 * Vaadin framework main page
 * 
 * @author Florian
 *
 */
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

	/**
	 * A helper method, which sets the title of the page
	 *
	 * @param e
	 */
	public static void setCurrentPageTitle(String viewName) {
		assert viewName != null;
		Page.getCurrent().setTitle(
				(viewName.length() == 0 ? Pages.DEFAULT.toString() : viewName)
						+ " - " + ProductData.getInstance().toString());

	}

}