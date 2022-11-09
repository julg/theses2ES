package fr.abes.theses2ES.dto;

import fr.abes.theses2ES.model.jaxb.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.xpath.operations.Or;

import java.util.*;

@Slf4j
public class TheseMappee {

    //String id;
    String cas;
    String accessible;
    String codeEtab;
    String nnt;
    String dateSoutenance;
    String status;
    List<String> ppn;
    String source;
    Map<String, String> titres = new HashMap<String, String>();
    String titrePrincipal; // on veut ce titre dans un index à part pour faciliter l'affichage dans le front
    Map<String, String> resumes = new HashMap<String, String>();
    OrganismeDTO etabSoutenance = new OrganismeDTO();
    List<OrganismeDTO> etabCotutelle = new ArrayList<OrganismeDTO>();
    List<OrganismeDTO> ecolesDoctorales = new ArrayList<OrganismeDTO>();
    List<OrganismeDTO> partenairesRecherche = new ArrayList<OrganismeDTO>();
    List<PersonneDTO> rapporteurs = new ArrayList<PersonneDTO>();

    String discipline;
    List<PersonneDTO> auteurs = new ArrayList<PersonneDTO>();

    List<PersonneDTO> directeurs = new ArrayList<PersonneDTO>();

    PersonneDTO presidentJury = new PersonneDTO();
    List<PersonneDTO> membresJury = new ArrayList<PersonneDTO>();

    List<String> sujetsRameau = new ArrayList<String>();

    public TheseMappee(Mets mets) {
        try {
            DmdSec dmdSec = mets.getDmdSec().get(1);

            // id
            //id = dmdSec.getID();

            // cas
            cas = mets.getDmdSec().stream().filter(d -> d.getMdWrap().getXmlData().getStarGestion() != null).findFirst().orElse(null)
                    .getMdWrap().getXmlData().getStarGestion().getTraitements().getScenario();

            // accessible (calculé au moment de l'indexation)
            /*accessible = "non";
            String dateRestrictionFin = "";
            dateRestrictionFin = dmdSec.getMdWrap().getXmlData().getStarGestion().getTraitements().getSorties().getDiffusion().getRestrictionTemporelleFin();
            LocalDate dateRestrFin = LocalDate.parse(dateRestrictionFin);

            if ((cas.equals("cas1") || cas.equals("cas2") || cas.equals("cas3") || cas.equals("cas4"))
            && ((dateRestrictionFin == "") || dateRestrFin.isAfter(LocalDate.now())))
            {
                accessible = "oui";
            }*/

            // codeEtab
            //codeEtab = dmdSec.getMdWrap().getXmlData().getStarGestion().getCodeEtab();

            // titrePrincipal

            titrePrincipal = dmdSec.getMdWrap().getXmlData().getThesisRecord().getTitle().getContent();

            // titres
            titres.put(
                    dmdSec.getMdWrap().getXmlData().getThesisRecord().getTitle().getLang(),
                    dmdSec.getMdWrap().getXmlData().getThesisRecord().getTitle().getContent());

            if (dmdSec.getMdWrap().getXmlData().getThesisRecord().getAlternative() != null) {
                Iterator<Alternative> titreAlternativeIterator = dmdSec.getMdWrap().getXmlData().getThesisRecord().getAlternative().iterator();
                while(titreAlternativeIterator.hasNext()) {
                    Alternative a = titreAlternativeIterator.next();
                    titres.put(
                            a.getLang(), a.getContent());
                }
            }

            // resumes
            List<Abstract> abstracts = dmdSec.getMdWrap().getXmlData().getThesisRecord().getAbstract();
            Iterator<Abstract> abstractIterator = abstracts.iterator();
            while (abstractIterator.hasNext()) {
                Abstract a = abstractIterator.next();
                resumes.put(a.getLang(), a.getContent());
            }

            AmdSec amdSec = mets.getAmdSec();

            // nnt
            List<Identifier> identifiers = amdSec.getTechMD().getMdWrap().getXmlData().getThesisAdmin().getIdentifier();
            Iterator<Identifier> iteIdentifiers = identifiers.iterator();
            while (iteIdentifiers.hasNext()) {
                Identifier i = iteIdentifiers.next();
                if (isNnt(i.getValue()))
                    nnt = i.getValue();
            }

            // date de soutenance

            dateSoutenance = amdSec.getTechMD().getMdWrap().getXmlData().getThesisAdmin().getDateAccepted().getValue().toString();

            // etablissements

            List<ThesisDegreeGrantor> grantors = amdSec.getTechMD().getMdWrap().getXmlData().getThesisAdmin()
                    .getThesisDegree().getThesisDegreeGrantor();
            Iterator<ThesisDegreeGrantor> iteGrantor = grantors.iterator();
            // l'étab de soutenance est le premier de la liste
            ThesisDegreeGrantor premier = iteGrantor.next();
            etabSoutenance.setPpn(premier.getAutoriteExterne().getValue());
            etabSoutenance.setNom(premier.getNom());
            // les potentiels suivants sont les cotutelles
            while (iteGrantor.hasNext()) {
                ThesisDegreeGrantor a = iteGrantor.next();
                OrganismeDTO ctdto = new OrganismeDTO();
                ctdto.setPpn(a.getAutoriteExterne().getValue());
                ctdto.setNom(a.getNom());
                etabCotutelle.add(ctdto);
            }

            // partenaires

            List<PartenaireRecherche> partenairesDepuisTef = amdSec.getTechMD().getMdWrap().getXmlData().getThesisAdmin()
                    .getPartenaireRecherche();
            Iterator<PartenaireRecherche> partenairesIterator = partenairesDepuisTef.iterator();
            while (partenairesIterator.hasNext()) {
                PartenaireRecherche p = partenairesIterator.next();
                OrganismeDTO pdto = new OrganismeDTO();
                pdto.setPpn(p.getAutoriteExterne().getValue());
                pdto.setNom(p.getNom());
                partenairesRecherche.add(pdto);
            }

            // ecoles doctorales

            List<EcoleDoctorale> ecolesDoctoralesDepuisTef = amdSec.getTechMD().getMdWrap().getXmlData().getThesisAdmin()
                    .getEcoleDoctorale();
            Iterator<EcoleDoctorale> ecoleDoctoraleIterator = ecolesDoctoralesDepuisTef.iterator();
            while (ecoleDoctoraleIterator.hasNext()) {
                EcoleDoctorale ecole = ecoleDoctoraleIterator.next();
                OrganismeDTO edto = new OrganismeDTO();
                edto.setPpn(ecole.getAutoriteExterne().getValue());
                edto.setNom(ecole.getNom());
                ecolesDoctorales.add(edto);
            }

            // discipline

            ThesisDegreeDiscipline tddisc = amdSec.getTechMD().getMdWrap().getXmlData().getThesisAdmin()
                    .getThesisDegree().getThesisDegreeDiscipline();
            discipline = tddisc.getValue();

            // auteurs

            List<Auteur> auteursDepuisTef = amdSec.getTechMD().getMdWrap().getXmlData().getThesisAdmin()
                    .getAuteur();
            Iterator<Auteur> auteurIterator = auteursDepuisTef.iterator();
            while (auteurIterator.hasNext()) {
                Auteur a = auteurIterator.next();
                PersonneDTO adto = new PersonneDTO();
                adto.setPpn(a.getAutoriteExterne().getValue());
                adto.setNom(a.getNom());
                adto.setPrenom(a.getPrenom());
                auteurs.add(adto);
            }

            // directeurs
            List<DirecteurThese> directeursDepuisTef = amdSec.getTechMD().getMdWrap().getXmlData().getThesisAdmin()
                    .getDirecteurThese();
            Iterator<DirecteurThese> directeurTheseIterator = directeursDepuisTef.iterator();
            while (directeurTheseIterator.hasNext()) {
                DirecteurThese dt = directeurTheseIterator.next();
                PersonneDTO dtdto = new PersonneDTO();
                dtdto.setPpn(dt.getAutoriteExterne().getValue());
                dtdto.setNom(dt.getNom());
                dtdto.setPrenom(dt.getPrenom());
                directeurs.add(dtdto);
            }

            // presidentJury

            if (amdSec.getTechMD().getMdWrap().getXmlData().getThesisAdmin().getPresidentJury() != null) {
                PresidentJury presidentDepuisTef = amdSec.getTechMD().getMdWrap().getXmlData().getThesisAdmin().getPresidentJury();
                presidentJury.setPpn(presidentDepuisTef.getAutoriteExterne().getValue());
                presidentJury.setNom(presidentDepuisTef.getNom());
                presidentJury.setPrenom(presidentDepuisTef.getPrenom());
            }

            // membres Jury

            List<MembreJury> membresDepuisTef = amdSec.getTechMD().getMdWrap().getXmlData().getThesisAdmin()
                    .getMembreJury();
            Iterator<MembreJury> membresIterator = membresDepuisTef.iterator();
            while (membresIterator.hasNext()) {
                MembreJury m = membresIterator.next();
                PersonneDTO mdto = new PersonneDTO();
                mdto.setPpn(m.getAutoriteExterne().getValue());
                mdto.setNom(m.getNom());
                mdto.setPrenom(m.getPrenom());
                membresJury.add(mdto);
            }

            // rapporteurs

            List<Rapporteur> rapporteursDepuisTef = amdSec.getTechMD().getMdWrap().getXmlData().getThesisAdmin()
                    .getRapporteur();
            Iterator<Rapporteur> rapporteurIterator = rapporteursDepuisTef.iterator();
            while (rapporteurIterator.hasNext()) {
                Rapporteur r = rapporteurIterator.next();
                PersonneDTO rdto = new PersonneDTO();
                rdto.setPpn(r.getAutoriteExterne().getValue());
                rdto.setNom(r.getNom());
                rdto.setPrenom(r.getPrenom());
                rapporteurs.add(rdto);
            }

            // sujetsRameau

            List<VedetteRameauNomCommun> sujetsRameauDepuisTef = dmdSec.getMdWrap().getXmlData()
                    .getThesisRecord().getSujetRameau().getVedetteRameauNomCommun();
            Iterator<VedetteRameauNomCommun> vedetteRameauNomCommunIterator = sujetsRameauDepuisTef.iterator();
            while (vedetteRameauNomCommunIterator.hasNext()) {
                VedetteRameauNomCommun vdto = vedetteRameauNomCommunIterator.next();
                sujetsRameau.add(vdto.getElementdEntree().getContent());
            }


        } catch (Exception e) {
            log.error("erreur lors du mappage = " + e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    private boolean isNnt (String identifier) {
        if (identifier.length() == 12)
            return true;
        return false;
    }



    public String getNnt() {
        return nnt;
    }

    public void setNnt(String nnt) {
        this.nnt = nnt;
    }

    public OrganismeDTO getEtabSoutenance() {
        return etabSoutenance;
    }

    public void setEtabSoutenance(OrganismeDTO etabSoutenance) {
        this.etabSoutenance = etabSoutenance;
    }

    public List<OrganismeDTO> getPartenairesRecherche() {
        return partenairesRecherche;
    }

    public void setPartenairesRecherche(List<OrganismeDTO> partenairesRecherche) {
        this.partenairesRecherche = partenairesRecherche;
    }

    public List<PersonneDTO> getRapporteurs() {
        return rapporteurs;
    }

    public void setRapporteurs(List<PersonneDTO> rapporteurs) {
        this.rapporteurs = rapporteurs;
    }

    public List<PersonneDTO> getMembresJury() {
        return membresJury;
    }

    public void setMembresJury(List<PersonneDTO> membresJury) {
        this.membresJury = membresJury;
    }
    public List<PersonneDTO> getAuteurs() {
        return auteurs;
    }

    public void setAuteurs(List<PersonneDTO> auteurs) {
        this.auteurs = auteurs;
    }

    public List<PersonneDTO> getDirecteurs() {
        return directeurs;
    }

    public void setDirecteurs(List<PersonneDTO> directeurs) {
        this.directeurs = directeurs;
    }
    public Map<String, String> getResumes() {
        return resumes;
    }

    public void setResumes(Map<String, String> resumes) {
        this.resumes = resumes;
    }
    public String getDateSoutenance() {
        return dateSoutenance;
    }

    public void setDateSoutenance(String dateSoutenance) {
        this.dateSoutenance = dateSoutenance;
    }

    /*public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/
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

    public Map<String,String> getTitres() {
        return titres;
    }

    public void setTitres(Map<String,String> titres) {
        this.titres = titres;
    }

    public List<OrganismeDTO> getEtabCotutelle() {
        return etabCotutelle;
    }

    public void setEtabCotutelle(List<OrganismeDTO> etabCotutelle) {
        this.etabCotutelle = etabCotutelle;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public List<OrganismeDTO> getEcolesDoctorales() {
        return ecolesDoctorales;
    }

    public void setEcolesDoctorales(List<OrganismeDTO> ecolesDoctorales) {
        this.ecolesDoctorales = ecolesDoctorales;
    }

}
