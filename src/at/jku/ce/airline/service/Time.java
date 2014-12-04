
package at.jku.ce.airline.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for time complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="time">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="indexDayOfWeek" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="timeOfDay" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "time", propOrder = {
    "indexDayOfWeek",
    "timeOfDay"
})
public class Time {

    protected int indexDayOfWeek;
    protected long timeOfDay;

    /**
     * Gets the value of the indexDayOfWeek property.
     * 
     */
    public int getIndexDayOfWeek() {
        return indexDayOfWeek;
    }

    /**
     * Sets the value of the indexDayOfWeek property.
     * 
     */
    public void setIndexDayOfWeek(int value) {
        this.indexDayOfWeek = value;
    }

    /**
     * Gets the value of the timeOfDay property.
     * 
     */
    public long getTimeOfDay() {
        return timeOfDay;
    }

    /**
     * Sets the value of the timeOfDay property.
     * 
     */
    public void setTimeOfDay(long value) {
        this.timeOfDay = value;
    }

}
