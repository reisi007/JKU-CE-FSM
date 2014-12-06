package at.reisisoft.fsm.crawler;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartUp implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if (!t.isInterrupted()) {
			try {
				t.interrupt();
			} catch (Exception e) {

			}
		}
	}

	private Thread t = null;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		String uddiXmlUrl = arg0.getServletContext().getRealPath("WEB-INF")
				+ File.separator + "uddi.xml";
		if (t == null) {
			t = new Thread(new Crawler(uddiXmlUrl));
			t.start();
		}
	}
}
