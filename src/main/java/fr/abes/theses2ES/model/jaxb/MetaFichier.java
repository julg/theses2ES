//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2018.09.12 à 05:12:30 PM CEST 
//


package fr.abes.theses2ES.model.jaxb;

import java.math.BigInteger;
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
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.abes.fr/abes/documents/tef}formatFichier"/&gt;
 *         &lt;element ref="{http://www.abes.fr/abes/documents/tef}taille"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "formatFichier",
    "taille"
})
@XmlRootElement(name = "meta_fichier")
public class MetaFichier {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String formatFichier;
    @XmlElement(required = true)
    protected BigInteger taille;

    /**
     * Obtient la valeur de la propriété formatFichier.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormatFichier() {
        return formatFichier;
    }

    /**
     * Définit la valeur de la propriété formatFichier.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormatFichier(String value) {
        this.formatFichier = value;
    }

    /**
     * Obtient la valeur de la propriété taille.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTaille() {
        return taille;
    }

    /**
     * Définit la valeur de la propriété taille.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTaille(BigInteger value) {
        this.taille = value;
    }

}
