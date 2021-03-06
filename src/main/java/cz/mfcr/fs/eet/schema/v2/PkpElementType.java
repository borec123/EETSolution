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
 * <p>Java class for PkpElementType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PkpElementType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://fs.mfcr.cz/eet/schema/v2>PkpType">
 *       &lt;attribute name="digest" use="required" type="{http://fs.mfcr.cz/eet/schema/v2}PkpDigestType" />
 *       &lt;attribute name="cipher" use="required" type="{http://fs.mfcr.cz/eet/schema/v2}PkpCipherType" />
 *       &lt;attribute name="encoding" use="required" type="{http://fs.mfcr.cz/eet/schema/v2}PkpEncodingType" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PkpElementType", propOrder = {
    "value"
})
public class PkpElementType {

    @XmlValue
    protected byte[] value;
    @XmlAttribute(name = "digest", required = true)
    protected PkpDigestType digest;
    @XmlAttribute(name = "cipher", required = true)
    protected PkpCipherType cipher;
    @XmlAttribute(name = "encoding", required = true)
    protected PkpEncodingType encoding;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setValue(byte[] value) {
        this.value = value;
    }

    /**
     * Gets the value of the digest property.
     * 
     * @return
     *     possible object is
     *     {@link PkpDigestType }
     *     
     */
    public PkpDigestType getDigest() {
        return digest;
    }

    /**
     * Sets the value of the digest property.
     * 
     * @param value
     *     allowed object is
     *     {@link PkpDigestType }
     *     
     */
    public void setDigest(PkpDigestType value) {
        this.digest = value;
    }

    /**
     * Gets the value of the cipher property.
     * 
     * @return
     *     possible object is
     *     {@link PkpCipherType }
     *     
     */
    public PkpCipherType getCipher() {
        return cipher;
    }

    /**
     * Sets the value of the cipher property.
     * 
     * @param value
     *     allowed object is
     *     {@link PkpCipherType }
     *     
     */
    public void setCipher(PkpCipherType value) {
        this.cipher = value;
    }

    /**
     * Gets the value of the encoding property.
     * 
     * @return
     *     possible object is
     *     {@link PkpEncodingType }
     *     
     */
    public PkpEncodingType getEncoding() {
        return encoding;
    }

    /**
     * Sets the value of the encoding property.
     * 
     * @param value
     *     allowed object is
     *     {@link PkpEncodingType }
     *     
     */
    public void setEncoding(PkpEncodingType value) {
        this.encoding = value;
    }

}
