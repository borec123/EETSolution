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
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for BkpElementType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BkpElementType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://fs.mfcr.cz/eet/schema/v2>BkpType">
 *       &lt;attribute name="digest" use="required" type="{http://fs.mfcr.cz/eet/schema/v2}BkpDigestType" />
 *       &lt;attribute name="encoding" use="required" type="{http://fs.mfcr.cz/eet/schema/v2}BkpEncodingType" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BkpElementType", propOrder = {
    "value"
})
public class BkpElementType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "digest", required = true)
    protected BkpDigestType digest;
    @XmlAttribute(name = "encoding", required = true)
    protected BkpEncodingType encoding;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the digest property.
     * 
     * @return
     *     possible object is
     *     {@link BkpDigestType }
     *     
     */
    public BkpDigestType getDigest() {
        return digest;
    }

    /**
     * Sets the value of the digest property.
     * 
     * @param value
     *     allowed object is
     *     {@link BkpDigestType }
     *     
     */
    public void setDigest(BkpDigestType value) {
        this.digest = value;
    }

    /**
     * Gets the value of the encoding property.
     * 
     * @return
     *     possible object is
     *     {@link BkpEncodingType }
     *     
     */
    public BkpEncodingType getEncoding() {
        return encoding;
    }

    /**
     * Sets the value of the encoding property.
     * 
     * @param value
     *     allowed object is
     *     {@link BkpEncodingType }
     *     
     */
    public void setEncoding(BkpEncodingType value) {
        this.encoding = value;
    }

}