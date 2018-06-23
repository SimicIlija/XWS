//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.23 at 07:47:12 PM CEST 
//


package com.xml.booking.backendmain.ws_classes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for reservationXML complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reservationXML">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="accommodation" type="{http://booking.xml.com/backendmain/ws-classes}accommodationXML"/>
 *         &lt;element name="user" type="{http://booking.xml.com/backendmain/ws-classes}userXML"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="confirmed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reservationXML", propOrder = {
    "id",
    "accommodation",
    "user",
    "startDate",
    "endDate",
    "confirmed"
})
public class ReservationXML {

    protected long id;
    @XmlElement(required = true)
    protected AccommodationXML accommodation;
    @XmlElement(required = true)
    protected UserXML user;
    protected long startDate;
    protected long endDate;
    protected boolean confirmed;

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the accommodation property.
     * 
     * @return
     *     possible object is
     *     {@link AccommodationXML }
     *     
     */
    public AccommodationXML getAccommodation() {
        return accommodation;
    }

    /**
     * Sets the value of the accommodation property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccommodationXML }
     *     
     */
    public void setAccommodation(AccommodationXML value) {
        this.accommodation = value;
    }

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link UserXML }
     *     
     */
    public UserXML getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserXML }
     *     
     */
    public void setUser(UserXML value) {
        this.user = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     */
    public long getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     */
    public void setStartDate(long value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     */
    public long getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     */
    public void setEndDate(long value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the confirmed property.
     * 
     */
    public boolean isConfirmed() {
        return confirmed;
    }

    /**
     * Sets the value of the confirmed property.
     * 
     */
    public void setConfirmed(boolean value) {
        this.confirmed = value;
    }

}
