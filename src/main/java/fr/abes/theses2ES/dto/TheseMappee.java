package fr.abes.theses2ES.dto;

import fr.abes.theses2ES.model.jaxb.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class TheseMappee {

    String id;
    String dateMaj;
    String status;
    List<String> ppn;
    String source;
    String accessible;
    String titre;
    String titre2;
    String auteurPpn;
    String abstractEN;
    String abstractES;
    String abstractFR;
    String abstractXX;
    List<String> etablissements;
    String discipline;

    public TheseMappee(Mets mets) {
        try {
            DmdSec dmdSec = mets.getDmdSec().get(1);
            id = dmdSec.getID();

            // titre1
            titre = dmdSec.getMdWrap().getXmlData().getThesisRecord().getTitle().getContent();
            // titre2
            if (dmdSec.getMdWrap().getXmlData().getThesisRecord().getAlternative() != null) {
                titre2 = dmdSec.getMdWrap().getXmlData().getThesisRecord().getAlternative().getContent();
            }

            // abstracts
            List<Abstract> abstracts = dmdSec.getMdWrap().getXmlData().getThesisRecord().getAbstract();
            Iterator<Abstract> ite = abstracts.iterator();
            while (ite.hasNext()) {
                Abstract a = ite.next();
                if (a.getLang().contains("fr")) {
                    abstractFR = a.getContent();
                }
                if (a.getLang().contains("en")) {
                    abstractEN = a.getContent();
                }
                if (a.getLang().contains("es")) {
                    abstractES = a.getContent();
                }
                if (!a.getLang().contains("fr") && !a.getLang().contains("es") && !a.getLang().contains("en")) {
                    abstractXX = a.getContent();
                }
            }

            // établissements
            AmdSec amdSec = mets.getAmdSec();
            etablissements = new ArrayList<>();
            if (!amdSec.getTechMD().isEmpty()) {
                List<ThesisDegreeGrantor> grantors = amdSec.getTechMD().get(0).getMdWrap().getXmlData().getThesisAdmin()
                        .getThesisDegree().getThesisDegreeGrantor();
                Iterator<ThesisDegreeGrantor> iteGrantor = grantors.iterator();
                while (iteGrantor.hasNext()) {
                    ThesisDegreeGrantor a = iteGrantor.next();
                    etablissements.add(a.getNom());
                }
            }

            // discipline
            if (!amdSec.getTechMD().isEmpty()) {
                ThesisDegreeDiscipline tddisc = amdSec.getTechMD().get(0).getMdWrap().getXmlData().getThesisAdmin()
                        .getThesisDegree().getThesisDegreeDiscipline();
                discipline = tddisc.getValue();
            }

        } catch (Exception e) {
            log.error("erreur lors du mappage = " + e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    public String getAbstractFR() {
        return abstractFR;
    }

    public void setAbstractFR(String abstractFR) {
        this.abstractFR = abstractFR;
    }

    public String getAbstractXX() {
        return abstractXX;
    }

    public void setAbstractXX(String abstractXX) {
        this.abstractXX = abstractXX;
    }

    public String getAbstractES() {
        return abstractES;
    }

    public void setAbstractES(String abstractES) {
        this.abstractES = abstractES;
    }

    public String getAbstractEN() {
        return abstractEN;
    }

    public void setAbstractEN(String abstractEN) {
        this.abstractEN = abstractEN;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateMaj() {
        return dateMaj;
    }

    public void setDateMaj(String dateMaj) {
        this.dateMaj = dateMaj;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getPpn() {
        return ppn;
    }

    public void setPpn(List<String> ppn) {
        this.ppn = ppn;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAccessible() {
        return accessible;
    }

    public void setAccessible(String accessible) {
        this.accessible = accessible;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTitre2() {
        return titre2;
    }

    public void setTitre2(String titre2) {
        this.titre2 = titre2;
    }

    public String getAuteurPpn() {
        return auteurPpn;
    }

    public void setAuteurPpn(String auteurPpn) {
        this.auteurPpn = auteurPpn;
    }

    public List<String> getEtablissements() {
        return etablissements;
    }

    public void setEtablissements(List<String> etablissements) {
        this.etablissements = etablissements;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }
}
