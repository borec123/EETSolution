//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.03 at 02:20:14 AM CEST 
//


package cz.mfcr.fs.eet.schema.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TrzbaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TrzbaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Hlavicka" type="{http://fs.mfcr.cz/eet/schema/v2}TrzbaHlavickaType"/>
 *         &lt;element name="Data" type="{http://fs.mfcr.cz/eet/schema/v2}TrzbaDataType"/>
 *         &lt;element name="KontrolniKody" type="{http://fs.mfcr.cz/eet/schema/v2}TrzbaKontrolniKodyType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TrzbaType", propOrder = {
    "hlavicka",
    "data",
    "kontrolniKody"
})
public class TrzbaType {

    @XmlElement(name = "Hlavicka", required = true)
    protected TrzbaHlavickaType hlavicka;
    @XmlElement(name = "Data", required = true)
    protected TrzbaDataType data;
    @XmlElement(name = "KontrolniKody", required = true)
    protected TrzbaKontrolniKodyType kontrolniKody;

    /**
     * Gets the value of the hlavicka property.
     * 
     * @return
     *     possible object is
     *     {@link TrzbaHlavickaType }
     *     
     */
    public TrzbaHlavickaType getHlavicka() {
        return hlavicka;
    }

    /**
     * Sets the value of the hlavicka property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrzbaHlavickaType }
     *     
     */
    public void setHlavicka(TrzbaHlavickaType value) {
        this.hlavicka = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link TrzbaDataType }
     *     
     */
    public TrzbaDataType getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrzbaDataType }
     *     
     */
    public void setData(TrzbaDataType value) {
        this.data = value;
    }

    /**
     * Gets the value of the kontrolniKody property.
     * 
     * @return
     *     possible object is
     *     {@link TrzbaKontrolniKodyType }
     *     
     */
    public TrzbaKontrolniKodyType getKontrolniKody() {
        return kontrolniKody;
    }

    /**
     * Sets the value of the kontrolniKody property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrzbaKontrolniKodyType }
     *     
     */
    public void setKontrolniKody(TrzbaKontrolniKodyType value) {
        this.kontrolniKody = value;
    }

}