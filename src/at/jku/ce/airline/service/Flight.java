
package at.jku.ce.airline.service;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for flight complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="flight">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arrivalTime" type="{http://service.airline.ce.jku.at/}time" minOccurs="0"/>
 *         &lt;element name="arrivesAt" type="{http://service.airline.ce.jku.at/}airport" minOccurs="0"/>
 *         &lt;element name="departesFrom" type="{http://service.airline.ce.jku.at/}airport" minOccurs="0"/>
 *         &lt;element name="departureTime" type="{http://service.airline.ce.jku.at/}time" minOccurs="0"/>
 *         &lt;element name="flightId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="planeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="std_fee" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "flight", propOrder = {
    "arrivalTime",
    "arrivesAt",
    "departesFrom",
    "departureTime",
    "flightId",
    "planeName",
    "stdFee"
})
public class Flight {

    protected Time arrivalTime;
    protected Airport arrivesAt;
    protected Airport departesFrom;
    protected Time departureTime;
    protected String flightId;
    protected String planeName;
    @XmlElement(name = "std_fee")
    protected BigDecimal stdFee;

    /**
     * Gets the value of the arrivalTime property.
     * 
     * @return
     *     possible object is
     *     {@link Time }
     *     
     */
    public Time getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the value of the arrivalTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Time }
     *     
     */
    public void setArrivalTime(Time value) {
        this.arrivalTime = value;
    }

    /**
     * Gets the value of the arrivesAt property.
     * 
     * @return
     *     possible object is
     *     {@link Airport }
     *     
     */
    public Airport getArrivesAt() {
        return arrivesAt;
    }

    /**
     * Sets the value of the arrivesAt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Airport }
     *     
     */
    public void setArrivesAt(Airport value) {
        this.arrivesAt = value;
    }

    /**
     * Gets the value of the departesFrom property.
     * 
     * @return
     *     possible object is
     *     {@link Airport }
     *     
     */
    public Airport getDepartesFrom() {
        return departesFrom;
    }

    /**
     * Sets the value of the departesFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Airport }
     *     
     */
    public void setDepartesFrom(Airport value) {
        this.departesFrom = value;
    }

    /**
     * Gets the value of the departureTime property.
     * 
     * @return
     *     possible object is
     *     {@link Time }
     *     
     */
    public Time getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the value of the departureTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Time }
     *     
     */
    public void setDepartureTime(Time value) {
        this.departureTime = value;
    }

    /**
     * Gets the value of the flightId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlightId() {
        return flightId;
    }

    /**
     * Sets the value of the flightId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlightId(String value) {
        this.flightId = value;
    }

    /**
     * Gets the value of the planeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlaneName() {
        return planeName;
    }

    /**
     * Sets the value of the planeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlaneName(String value) {
        this.planeName = value;
    }

    /**
     * Gets the value of the stdFee property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStdFee() {
        return stdFee;
    }

    /**
     * Sets the value of the stdFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStdFee(BigDecimal value) {
        this.stdFee = value;
    }

}
