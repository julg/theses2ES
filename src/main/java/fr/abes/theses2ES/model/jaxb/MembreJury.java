//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2022.11.17 � 02:52:49 PM CET 
//


package fr.abes.theses2ES.model.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         &lt;element ref="{http://www.abes.fr/abes/documents/tef}nom"/>
 *         &lt;element ref="{http://www.abes.fr/abes/documents/tef}prenom"/>
 *         &lt;element ref="{http://www.abes.fr/abes/documents/tef}autoriteInterne"/>
 *         &lt;element ref="{http://www.abes.fr/abes/documents/tef}autoriteExterne"/>
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
    "nom",
    "prenom",
    "autoriteInterne",
    "autoriteExterne"
})
@XmlRootElement(name = "membreJury")
public class MembreJury {

    @XmlElement(required = true)
    protected String nom;
    @XmlElement(required = true)
    protected String prenom;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String autoriteInterne;
    @XmlElement(required = true)
    protected AutoriteExterne autoriteExterne;

    /**
     * Obtient la valeur de la propri�t� nom.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNom() {
        return nom;
    }

    /**
     * D�finit la valeur de la propri�t� nom.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNom(String value) {
        this.nom = value;
    }

    /**
     * Obtient la valeur de la propri�t� prenom.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * D�finit la valeur de la propri�t� prenom.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrenom(String value) {
        this.prenom = value;
    }

    /**
     * Obtient la valeur de la propri�t� autoriteInterne.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutoriteInterne() {
        return autoriteInterne;
    }

    /**
     * D�finit la valeur de la propri�t� autoriteInterne.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutoriteInterne(String value) {
        this.autoriteInterne = value;
    }

    /**
     * Obtient la valeur de la propri�t� autoriteExterne.
     * 
     * @return
     *     possible object is
     *     {@link AutoriteExterne }
     *     
     */
    public AutoriteExterne getAutoriteExterne() {
        return autoriteExterne;
    }

    /**
     * D�finit la valeur de la propri�t� autoriteExterne.
     * 
     * @param value
     *     allowed object is
     *     {@link AutoriteExterne }
     *     
     */
    public void setAutoriteExterne(AutoriteExterne value) {
        this.autoriteExterne = value;
    }

}
