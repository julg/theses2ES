//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2022.11.17 � 02:52:49 PM CET 
//


package fr.abes.theses2ES.model.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.loc.gov/METS/}mdWrap" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "mdWrap"
})
@XmlRootElement(name = "techMD", namespace = "http://www.loc.gov/METS/")
public class TechMD {

    @XmlElement(namespace = "http://www.loc.gov/METS/")
    protected MdWrap mdWrap;
    @XmlAttribute(name = "ID")
    @XmlSchemaType(name = "anySimpleType")
    protected String id;

    /**
     * Obtient la valeur de la propri�t� mdWrap.
     * 
     * @return
     *     possible object is
     *     {@link MdWrap }
     *     
     */
    public MdWrap getMdWrap() {
        return mdWrap;
    }

    /**
     * D�finit la valeur de la propri�t� mdWrap.
     * 
     * @param value
     *     allowed object is
     *     {@link MdWrap }
     *     
     */
    public void setMdWrap(MdWrap value) {
        this.mdWrap = value;
    }

    /**
     * Obtient la valeur de la propri�t� id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * D�finit la valeur de la propri�t� id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

}
