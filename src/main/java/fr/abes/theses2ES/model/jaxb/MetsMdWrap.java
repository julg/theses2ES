//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2022.11.08 � 03:14:28 PM CET 
//


package fr.abes.theses2ES.model.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour mets.mdWrap complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="mets.mdWrap">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.loc.gov/METS/}mdWrap"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mets.mdWrap", propOrder = {
    "mdWrap"
})
@XmlSeeAlso({
    DmdSec.class,
    TechMD.class
})
public class MetsMdWrap {

    @XmlElement(namespace = "http://www.loc.gov/METS/", required = true)
    protected MdWrap mdWrap;

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

}
