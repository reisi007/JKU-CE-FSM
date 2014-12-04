
package at.jku.ce.airline.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the at.jku.ce.airline.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetAirline_QNAME = new QName("http://service.airline.ce.jku.at/", "getAirline");
    private final static QName _GetFlightResponse_QNAME = new QName("http://service.airline.ce.jku.at/", "getFlightResponse");
    private final static QName _BookFlight_QNAME = new QName("http://service.airline.ce.jku.at/", "bookFlight");
    private final static QName _CancelFlightResponse_QNAME = new QName("http://service.airline.ce.jku.at/", "cancelFlightResponse");
    private final static QName _BookFlightResponse_QNAME = new QName("http://service.airline.ce.jku.at/", "bookFlightResponse");
    private final static QName _GetFlightplanResponse_QNAME = new QName("http://service.airline.ce.jku.at/", "getFlightplanResponse");
    private final static QName _GetFlight_QNAME = new QName("http://service.airline.ce.jku.at/", "getFlight");
    private final static QName _GetAirlineResponse_QNAME = new QName("http://service.airline.ce.jku.at/", "getAirlineResponse");
    private final static QName _GetFlightplan_QNAME = new QName("http://service.airline.ce.jku.at/", "getFlightplan");
    private final static QName _CancelFlight_QNAME = new QName("http://service.airline.ce.jku.at/", "cancelFlight");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: at.jku.ce.airline.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAirline }
     * 
     */
    public GetAirline createGetAirline() {
        return new GetAirline();
    }

    /**
     * Create an instance of {@link GetFlightResponse }
     * 
     */
    public GetFlightResponse createGetFlightResponse() {
        return new GetFlightResponse();
    }

    /**
     * Create an instance of {@link CancelFlightResponse }
     * 
     */
    public CancelFlightResponse createCancelFlightResponse() {
        return new CancelFlightResponse();
    }

    /**
     * Create an instance of {@link BookFlight }
     * 
     */
    public BookFlight createBookFlight() {
        return new BookFlight();
    }

    /**
     * Create an instance of {@link GetFlightplanResponse }
     * 
     */
    public GetFlightplanResponse createGetFlightplanResponse() {
        return new GetFlightplanResponse();
    }

    /**
     * Create an instance of {@link BookFlightResponse }
     * 
     */
    public BookFlightResponse createBookFlightResponse() {
        return new BookFlightResponse();
    }

    /**
     * Create an instance of {@link GetFlight }
     * 
     */
    public GetFlight createGetFlight() {
        return new GetFlight();
    }

    /**
     * Create an instance of {@link GetFlightplan }
     * 
     */
    public GetFlightplan createGetFlightplan() {
        return new GetFlightplan();
    }

    /**
     * Create an instance of {@link GetAirlineResponse }
     * 
     */
    public GetAirlineResponse createGetAirlineResponse() {
        return new GetAirlineResponse();
    }

    /**
     * Create an instance of {@link CancelFlight }
     * 
     */
    public CancelFlight createCancelFlight() {
        return new CancelFlight();
    }

    /**
     * Create an instance of {@link Flight }
     * 
     */
    public Flight createFlight() {
        return new Flight();
    }

    /**
     * Create an instance of {@link Airport }
     * 
     */
    public Airport createAirport() {
        return new Airport();
    }

    /**
     * Create an instance of {@link Time }
     * 
     */
    public Time createTime() {
        return new Time();
    }

    /**
     * Create an instance of {@link Airline }
     * 
     */
    public Airline createAirline() {
        return new Airline();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAirline }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.airline.ce.jku.at/", name = "getAirline")
    public JAXBElement<GetAirline> createGetAirline(GetAirline value) {
        return new JAXBElement<GetAirline>(_GetAirline_QNAME, GetAirline.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFlightResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.airline.ce.jku.at/", name = "getFlightResponse")
    public JAXBElement<GetFlightResponse> createGetFlightResponse(GetFlightResponse value) {
        return new JAXBElement<GetFlightResponse>(_GetFlightResponse_QNAME, GetFlightResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookFlight }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.airline.ce.jku.at/", name = "bookFlight")
    public JAXBElement<BookFlight> createBookFlight(BookFlight value) {
        return new JAXBElement<BookFlight>(_BookFlight_QNAME, BookFlight.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelFlightResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.airline.ce.jku.at/", name = "cancelFlightResponse")
    public JAXBElement<CancelFlightResponse> createCancelFlightResponse(CancelFlightResponse value) {
        return new JAXBElement<CancelFlightResponse>(_CancelFlightResponse_QNAME, CancelFlightResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookFlightResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.airline.ce.jku.at/", name = "bookFlightResponse")
    public JAXBElement<BookFlightResponse> createBookFlightResponse(BookFlightResponse value) {
        return new JAXBElement<BookFlightResponse>(_BookFlightResponse_QNAME, BookFlightResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFlightplanResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.airline.ce.jku.at/", name = "getFlightplanResponse")
    public JAXBElement<GetFlightplanResponse> createGetFlightplanResponse(GetFlightplanResponse value) {
        return new JAXBElement<GetFlightplanResponse>(_GetFlightplanResponse_QNAME, GetFlightplanResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFlight }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.airline.ce.jku.at/", name = "getFlight")
    public JAXBElement<GetFlight> createGetFlight(GetFlight value) {
        return new JAXBElement<GetFlight>(_GetFlight_QNAME, GetFlight.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAirlineResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.airline.ce.jku.at/", name = "getAirlineResponse")
    public JAXBElement<GetAirlineResponse> createGetAirlineResponse(GetAirlineResponse value) {
        return new JAXBElement<GetAirlineResponse>(_GetAirlineResponse_QNAME, GetAirlineResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFlightplan }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.airline.ce.jku.at/", name = "getFlightplan")
    public JAXBElement<GetFlightplan> createGetFlightplan(GetFlightplan value) {
        return new JAXBElement<GetFlightplan>(_GetFlightplan_QNAME, GetFlightplan.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelFlight }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.airline.ce.jku.at/", name = "cancelFlight")
    public JAXBElement<CancelFlight> createCancelFlight(CancelFlight value) {
        return new JAXBElement<CancelFlight>(_CancelFlight_QNAME, CancelFlight.class, null, value);
    }

}
