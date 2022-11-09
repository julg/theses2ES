//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2022.11.08 � 03:14:28 PM CET 
//


package fr.abes.theses2ES.model.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
 *         &lt;element ref="{http://www.loc.gov/METS/}metsHdr"/>
 *         &lt;element ref="{http://www.loc.gov/METS/}dmdSec" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.loc.gov/METS/}amdSec"/>
 *         &lt;element ref="{http://www.loc.gov/METS/}structMap"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "metsHdr",
    "dmdSec",
    "amdSec",
    "structMap"
})
@XmlRootElement(name = "mets", namespace = "http://www.loc.gov/METS/")
public class Mets {

    @XmlElement(namespace = "http://www.loc.gov/METS/", required = true)
    protected MetsHdr metsHdr;
    @XmlElement(namespace = "http://www.loc.gov/METS/", required = true)
    protected List<DmdSec> dmdSec;
    @XmlElement(namespace = "http://www.loc.gov/METS/", required = true)
    protected AmdSec amdSec;
    @XmlElement(namespace = "http://www.loc.gov/METS/", required = true)
    protected StructMap structMap;

    /**
     * Obtient la valeur de la propri�t� metsHdr.
     * 
     * @return
     *     possible object is
     *     {@link MetsHdr }
     *     
     */
    public MetsHdr getMetsHdr() {
        return metsHdr;
    }

    /**
     * D�finit la valeur de la propri�t� metsHdr.
     * 
     * @param value
     *     allowed object is
     *     {@link MetsHdr }
     *     
     */
    public void setMetsHdr(MetsHdr value) {
        this.metsHdr = value;
    }

    /**
     * Gets the value of the dmdSec property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dmdSec property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDmdSec().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DmdSec }
     * 
     * 
     */
    public List<DmdSec> getDmdSec() {
        if (dmdSec == null) {
            dmdSec = new ArrayList<DmdSec>();
        }
        return this.dmdSec;
    }

    /**
     * Obtient la valeur de la propri�t� amdSec.
     * 
     * @return
     *     possible object is
     *     {@link AmdSec }
     *     
     */
    public AmdSec getAmdSec() {
        return amdSec;
    }

    /**
     * D�finit la valeur de la propri�t� amdSec.
     * 
     * @param value
     *     allowed object is
     *     {@link AmdSec }
     *     
     */
    public void setAmdSec(AmdSec value) {
        this.amdSec = value;
    }

    /**
     * Obtient la valeur de la propri�t� structMap.
     * 
     * @return
     *     possible object is
     *     {@link StructMap }
     *     
     */
    public StructMap getStructMap() {
        return structMap;
    }

    /**
     * D�finit la valeur de la propri�t� structMap.
     * 
     * @param value
     *     allowed object is
     *     {@link StructMap }
     *     
     */
    public void setStructMap(StructMap value) {
        this.structMap = value;
    }

}
