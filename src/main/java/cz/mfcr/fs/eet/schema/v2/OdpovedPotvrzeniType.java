//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.03 at 02:20:14 AM CEST 
//


package cz.mfcr.fs.eet.schema.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OdpovedPotvrzeniType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OdpovedPotvrzeniType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="fik" use="required" type="{http://fs.mfcr.cz/eet/schema/v2}FikType" />
 *       &lt;attribute name="test" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OdpovedPotvrzeniType")
public class OdpovedPotvrzeniType {

    @XmlAttribute(name = "fik", required = true)
    protected String fik;
    @XmlAttribute(name = "test")
    protected Boolean test;

    /**
     * Gets the value of the fik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFik() {
        return fik;
    }

    /**
     * Sets the value of the fik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFik(String value) {
        this.fik = value;
    }

    /**
     * Gets the value of the test property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTest() {
        return test;
    }

    /**
     * Sets the value of the test property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTest(Boolean value) {
        this.test = value;
    }

}
