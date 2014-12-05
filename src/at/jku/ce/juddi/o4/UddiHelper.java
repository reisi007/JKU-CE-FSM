package at.jku.ce.juddi.o4;

import java.net.URL;

import at.jku.ce.airline.service.AirlineServiceImpl;
import at.jku.ce.airline.service.AirlineServiceImplService;
import at.jku.ce.juddi.UddiManager;
import at.reisisoft.fsm.FsmUI;

public class UddiHelper {

	public static UddiHelper getInstance() {
		if (helper == null) {
			helper = new UddiHelper();
		}
		return helper;
	}

	private static UddiHelper helper = null;

	private UddiHelper() {
	}

	public AirlineServiceImpl forAirline(String name) {
		UddiManager uddiManager = UddiManager.getInstance(FsmUI.uuidXMurl);
		for (String s : uddiManager.getAllPublishedAccessPoints()) {
			try {
				AirlineServiceImpl airline = getAirlineServiceImple(s);
				if (name.equals(airline.getAirline().getName())) {
					return airline;
				}
			} catch (Exception e) {

			}
		}
		return null;
	}

	private AirlineServiceImpl getAirlineServiceImple(String url) {
		try {
			return new AirlineServiceImplService(new URL(url))
			.getAirlineServiceImplPort();
		} catch (Exception e) {
			return null;
		}

	}
}
