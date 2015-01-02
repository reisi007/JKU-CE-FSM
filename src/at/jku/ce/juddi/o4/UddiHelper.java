package at.jku.ce.juddi.o4;

import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

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

	/**
	 *
	 * @param airline
	 *            The airline's name
	 * @param uuid
	 *            The key in the DB - the booking ID
	 * @param flightId
	 *            The ID of the flight
	 * @param date
	 *            The flight date
	 * @return {@literal true} IF airline exists and booking @ the airline is
	 *         sucessful
	 */
	public boolean book(String airline, String uuid, String flightId, Date date) {
		AirlineServiceImpl airlineServiceImpl = helper.forAirline(airline);
		GregorianCalendar g = new GregorianCalendar();
		g.setTime(date);
		XMLGregorianCalendar calendar;
		try {
			calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(g);
		} catch (DatatypeConfigurationException e) {
			return false;
		}
		return airlineServiceImpl.bookFlight(uuid, flightId, calendar);

	}

	/**
	 * @see #book(String, String, String, java.util.Date)
	 */
	public boolean book(String airline, String uuid, String flightId,
			java.sql.Date date) {
		return book(airline, uuid, flightId, new Date(date.getTime()));
	}

	/**
	 *
	 * @param name
	 *            Airline's name
	 * @return returns the {@link at.jku.ce.airline.service.AirlineServiceImpl}
	 *         IF the airline exists
	 */
	private AirlineServiceImpl forAirline(String name) {
		UddiManager uddiManager = getManager();
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

	/**
	 *
	 * @param url
	 *            A url to an
	 *            {@link at.jku.ce.airline.service.AirlineServiceImpl}
	 * @return null} if it does not exist, otherwise the
	 *         {@link at.jku.ce.airline.service.AirlineServiceImpl} - Objekt
	 */
	private AirlineServiceImpl getAirlineServiceImple(String url) {
		try {
			return new AirlineServiceImplService(new URL(url))
			.getAirlineServiceImplPort();
		} catch (Exception e) {
			return null;
		}
	}

	private UddiManager getManager() {
		return UddiManager.getInstance(FsmUI.uuidXMurl);
	}

	/**
	 *
	 * @param uudiCmlUrl
	 * @return {@link at.jku.ce.juddi.UddiManager}, {@inheritDoc
	 *         at.jku.ce.juddi.UddiManager#getInstance(String)}
	 */
	public UddiManager getManager(String uudiCmlUrl) {
		return UddiManager.getInstance(uudiCmlUrl);
	}

	/**
	 *
	 * @param airline
	 *            The airline's name
	 * @param uuid
	 *            The key in the DB - the booking ID
	 * @param flightId
	 *            The ID of the flight
	 * @return {@literal true} if airline exists and storno was sucessfull
	 */
	public boolean storno(String airline, String uuid, String flightId) {
		AirlineServiceImpl airlineServiceImpl = helper.forAirline(airline);
		if (airlineServiceImpl == null) {
			return false;
		}
		return airlineServiceImpl.cancelFlight(uuid, flightId);
	}
}
