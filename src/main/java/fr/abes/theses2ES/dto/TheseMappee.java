package fr.abes.theses2ES.dto;

import fr.abes.theses2ES.model.jaxb.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.*;

@Slf4j(topic = "TheseMappee")
public class TheseMappee {

    //String id;
    String cas;
    String accessible;
    String source;
    String status;
    String codeEtab;
    String nnt;
    String dateSoutenance;
    String dateFinEmbargo;
    List<String> ppn;

    Map<String, String> titres = new HashMap<String, String>();
    String titrePrincipal; // on veut ce titre dans un index à part pour faciliter l'affichage dans le front
    Map<String, String> resumes = new HashMap<String, String>();
    List<String> langues = new ArrayList<String>();
    OrganismeDTO etabSoutenance = new OrganismeDTO();
    List<OrganismeDTO> etabsCotutelle = new ArrayList<OrganismeDTO>();
    List<OrganismeDTO> ecolesDoctorales = new ArrayList<OrganismeDTO>();
    List<OrganismeDTO> partenairesRecherche = new ArrayList<OrganismeDTO>();
    List<PersonneDTO> rapporteurs = new ArrayList<PersonneDTO>();
    String discipline;
    List<PersonneDTO> auteurs = new ArrayList<PersonneDTO>();
    List<PersonneDTO> directeurs = new ArrayList<PersonneDTO>();
    PersonneDTO presidentJury = new PersonneDTO();
    List<PersonneDTO> membresJury = new ArrayList<PersonneDTO>();
    List<String> sujetsRameau = new ArrayList<String>();
    Map<String, String> sujets = new HashMap<String, String>();
    List<String> oaiSets = new ArrayList<String>();

    public TheseMappee(Mets mets) {
        try {

            DmdSec dmdSec = mets.getDmdSec().get(1);
            AmdSec amdSec = mets.getAmdSec();

            // nnt

            TechMD techMD = amdSec.getTechMD().stream().filter(d -> d.getMdWrap().getXmlData().getThesisAdmin() != null).findFirst().orElse(null);
            
            Iterator<Identifier> iteIdentifiers = techMD.getMdWrap().getXmlData().getThesisAdmin().getIdentifier().iterator();
            while (iteIdentifiers.hasNext()) {
                Identifier i = iteIdentifiers.next();
                if (isNnt(i.getValue()))
                    nnt = i.getValue();
            }

            //log.info("traitement de " + nnt);

            // id
            //id = dmdSec.getID();

            // cas
           // log.info("traitement de cas");
            cas = mets.getDmdSec().stream().filter(d -> d.getMdWrap().getXmlData().getStarGestion() != null).findFirst().orElse(null)
                    .getMdWrap().getXmlData().getStarGestion().getTraitements().getScenario();


            // codeEtab

          //  log.info("traitement de codeEtab");
            codeEtab = mets.getDmdSec().stream().filter(d -> d.getMdWrap().getXmlData().getStarGestion() != null).findFirst().orElse(null)
                    .getMdWrap().getXmlData().getStarGestion().getCodeEtab();

            // titrePrincipal

            //log.info("traitement de titrePrincipal");
            titrePrincipal = dmdSec.getMdWrap().getXmlData().getThesisRecord().getTitle().getContent();

            // titres
            //log.info("traitement de titres");
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
            //log.info("traitement de resumes");
            List<Abstract> abstracts = dmdSec.getMdWrap().getXmlData().getThesisRecord().getAbstract();
            Iterator<Abstract> abstractIterator = abstracts.iterator();
            while (abstractIterator.hasNext()) {
                Abstract a = abstractIterator.next();
                resumes.put(a.getLang(), a.getContent());
            }

            // langues

            //log.info("traitement de langues");
            List<Language> languages = dmdSec.getMdWrap().getXmlData().getThesisRecord().getLanguage();
            Iterator<Language> languageIterator = languages.iterator();
            while (languageIterator.hasNext()) {
                Language l = languageIterator.next();
                langues.add(l.getValue());
            }

            // date de soutenance

            //log.info("traitement de dateSoutenance");
            dateSoutenance = techMD.getMdWrap().getXmlData().getThesisAdmin().getDateAccepted().getValue().toString();

            // date de fin d'embargo

            //log.info("traitement de datefinembargo ");
            if (!mets.getDmdSec().stream().filter(d -> d.getMdWrap().getXmlData().getStarGestion() != null).findFirst().orElse(null)
                    .getMdWrap().getXmlData().getStarGestion().getTraitements().getSorties().getDiffusion().getRestrictionTemporelleFin().isEmpty())
                dateFinEmbargo = mets.getDmdSec().stream().filter(d -> d.getMdWrap().getXmlData().getStarGestion() != null).findFirst().orElse(null)
                    .getMdWrap().getXmlData().getStarGestion().getTraitements().getSorties().getDiffusion().getRestrictionTemporelleFin();

            // accessible

            //log.info("traitement de accessible");
            accessible = "non";

            if ((cas.equals("cas1") || cas.equals("cas2") || cas.equals("cas3") || cas.equals("cas4"))
            && (dateFinEmbargo == null || dateFinEmbargo.isEmpty() || LocalDate.parse(dateFinEmbargo).isBefore(LocalDate.now()))) {
                accessible = "oui";
            }

            // status

            //log.info("traitement de status");
            status = "soutenue";
            Optional<DmdSec> stepGestion = mets.getDmdSec().stream().filter(d -> d.getMdWrap().getXmlData().getStepGestion() != null).findFirst();
            if (stepGestion.isPresent())
                status = "enCours";


            // source
            //log.info("traitement de source");
            source = "sudoc";
            if (status.equals("enCours"))
                source = "step";
            try {
                if (status.equals("soutenue") && mets.getDmdSec().stream().filter(d -> d.getMdWrap().getXmlData().getStarGestion() != null).findFirst().orElse(null)
                        .getMdWrap().getXmlData().getStarGestion().getTraitements().getSorties().getCines().getIndicCines().equals("OK"))
                    source = "star";
            }
            catch (NullPointerException ex) {
                log.info("impossible de récupérer le getIndicCines pour " + nnt + "(NullPointerException)");
            }

            // etablissements

            //log.info("traitement de etablissements");
            List<ThesisDegreeGrantor> grantors = techMD.getMdWrap().getXmlData().getThesisAdmin()
                    .getThesisDegree().getThesisDegreeGrantor();
            Iterator<ThesisDegreeGrantor> iteGrantor = grantors.iterator();
            // l'étab de soutenance est le premier de la liste
            ThesisDegreeGrantor premier = iteGrantor.next();
            if (premier.getAutoriteExterne() != null)
                etabSoutenance.setPpn(premier.getAutoriteExterne().getValue());
            etabSoutenance.setNom(premier.getNom());
            // les potentiels suivants sont les cotutelles
            while (iteGrantor.hasNext()) {
                ThesisDegreeGrantor a = iteGrantor.next();
                OrganismeDTO ctdto = new OrganismeDTO();
                if (a.getAutoriteExterne() != null)
                    ctdto.setPpn(a.getAutoriteExterne().getValue());
                ctdto.setNom(a.getNom());
                etabsCotutelle.add(ctdto);
            }

            // partenaires

            //log.info("traitement de partenaires");
            List<PartenaireRecherche> partenairesDepuisTef = techMD.getMdWrap().getXmlData().getThesisAdmin()
                    .getPartenaireRecherche();
            Iterator<PartenaireRecherche> partenairesIterator = partenairesDepuisTef.iterator();
            while (partenairesIterator.hasNext()) {
                PartenaireRecherche p = partenairesIterator.next();
                OrganismeDTO pdto = new OrganismeDTO();
                if (p.getAutoriteExterne() != null)
                    pdto.setPpn(p.getAutoriteExterne().getValue());
                pdto.setNom(p.getNom());
                partenairesRecherche.add(pdto);
            }

            // ecoles doctorales

            //log.info("traitement de ecolesDoctorales");
            List<EcoleDoctorale> ecolesDoctoralesDepuisTef = techMD.getMdWrap().getXmlData().getThesisAdmin()
                    .getEcoleDoctorale();
            Iterator<EcoleDoctorale> ecoleDoctoraleIterator = ecolesDoctoralesDepuisTef.iterator();
            while (ecoleDoctoraleIterator.hasNext()) {
                EcoleDoctorale ecole = ecoleDoctoraleIterator.next();
                OrganismeDTO edto = new OrganismeDTO();
                if (ecole.getAutoriteExterne() != null)
                    edto.setPpn(ecole.getAutoriteExterne().getValue());
                edto.setNom(ecole.getNom());
                ecolesDoctorales.add(edto);
            }

            // discipline

            //log.info("traitement de discipline");
            ThesisDegreeDiscipline tddisc = techMD.getMdWrap().getXmlData().getThesisAdmin()
                    .getThesisDegree().getThesisDegreeDiscipline();
            discipline = tddisc.getValue();

            // auteurs

            //log.info("traitement de auteurs");
            List<Auteur> auteursDepuisTef = techMD.getMdWrap().getXmlData().getThesisAdmin()
                    .getAuteur();
            Iterator<Auteur> auteurIterator = auteursDepuisTef.iterator();
            while (auteurIterator.hasNext()) {
                Auteur a = auteurIterator.next();
                PersonneDTO adto = new PersonneDTO();
                if (a.getAutoriteExterne() != null)
                    adto.setPpn(a.getAutoriteExterne().getValue());
                adto.setNom(a.getNom());
                adto.setPrenom(a.getPrenom());
                auteurs.add(adto);
            }

            // directeurs
            //log.info("traitement de directeurs");
            List<DirecteurThese> directeursDepuisTef = techMD.getMdWrap().getXmlData().getThesisAdmin()
                    .getDirecteurThese();
            Iterator<DirecteurThese> directeurTheseIterator = directeursDepuisTef.iterator();
            while (directeurTheseIterator.hasNext()) {
                DirecteurThese dt = directeurTheseIterator.next();
                PersonneDTO dtdto = new PersonneDTO();
                if (dt.getAutoriteExterne() != null)
                    dtdto.setPpn(dt.getAutoriteExterne().getValue());
                dtdto.setNom(dt.getNom());
                dtdto.setPrenom(dt.getPrenom());
                directeurs.add(dtdto);
            }

            // presidentJury

            //log.info("traitement de president jury");
            if (techMD.getMdWrap().getXmlData().getThesisAdmin().getPresidentJury() != null) {
                PresidentJury presidentDepuisTef = techMD.getMdWrap().getXmlData().getThesisAdmin().getPresidentJury();
                if (presidentDepuisTef.getAutoriteExterne() != null)
                    presidentJury.setPpn(presidentDepuisTef.getAutoriteExterne().getValue());
                presidentJury.setNom(presidentDepuisTef.getNom());
                presidentJury.setPrenom(presidentDepuisTef.getPrenom());
            }

            // membres Jury

            //log.info("traitement de membres jury");
            List<MembreJury> membresDepuisTef = techMD.getMdWrap().getXmlData().getThesisAdmin()
                    .getMembreJury();
            Iterator<MembreJury> membresIterator = membresDepuisTef.iterator();
            while (membresIterator.hasNext()) {
                MembreJury m = membresIterator.next();
                PersonneDTO mdto = new PersonneDTO();
                if (m.getAutoriteExterne() != null)
                    mdto.setPpn(m.getAutoriteExterne().getValue());
                mdto.setNom(m.getNom());
                mdto.setPrenom(m.getPrenom());
                membresJury.add(mdto);
            }

            // rapporteurs

            //log.info("traitement de rapporteurs");
            List<Rapporteur> rapporteursDepuisTef = techMD.getMdWrap().getXmlData().getThesisAdmin()
                    .getRapporteur();
            Iterator<Rapporteur> rapporteurIterator = rapporteursDepuisTef.iterator();
            while (rapporteurIterator.hasNext()) {
                Rapporteur r = rapporteurIterator.next();
                PersonneDTO rdto = new PersonneDTO();
                if (r.getAutoriteExterne() != null)
                    rdto.setPpn(r.getAutoriteExterne().getValue());
                rdto.setNom(r.getNom());
                rdto.setPrenom(r.getPrenom());
                rapporteurs.add(rdto);
            }

            // sujets

            //log.info("traitement de sujets");
            List<Subject> subjects = dmdSec.getMdWrap().getXmlData().getThesisRecord().getSubject();
            Iterator<Subject> subjectIterator = subjects.iterator();
            while (subjectIterator.hasNext()) {
                Subject s = subjectIterator.next();
                sujets.put(s.getLang(), s.getContent());
            }

            // sujetsRameau

            //log.info("traitement de sujetsRameau");
            List<VedetteRameauNomCommun> sujetsRameauDepuisTef = dmdSec.getMdWrap().getXmlData()
                    .getThesisRecord().getSujetRameau().getVedetteRameauNomCommun();
            Iterator<VedetteRameauNomCommun> vedetteRameauNomCommunIterator = sujetsRameauDepuisTef.iterator();
            while (vedetteRameauNomCommunIterator.hasNext()) {
                VedetteRameauNomCommun vdto = vedetteRameauNomCommunIterator.next();
                if (vdto.getElementdEntree() != null)
                    sujetsRameau.add(vdto.getElementdEntree().getContent());
            }

            // oaiSets

            //log.info("traitement de oaiSets");
            oaiSets = techMD.getMdWrap().getXmlData().getThesisAdmin()
                    .getOaiSetSpec();


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

    public List<OrganismeDTO> getEtabsCotutelle() {
        return etabsCotutelle;
    }

    public void setEtabsCotutelle(List<OrganismeDTO> etabsCotutelle) {
        this.etabsCotutelle = etabsCotutelle;
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

    public Map<String, String> getSujets() {
        return sujets;
    }

    public void setSujets(Map<String, String> sujets) {
        this.sujets = sujets;
    }

    public String getCodeEtab() {
        return codeEtab;
    }

    public void setCodeEtab(String codeEtab) {
        this.codeEtab = codeEtab;
    }

    public String getDateFinEmbargo() {
        return dateFinEmbargo;
    }

    public void setDateFinEmbargo(String dateFinEmbargo) {
        this.dateFinEmbargo = dateFinEmbargo;
    }

    public List<String> getLangues() {
        return langues;
    }

    public void setLangues(List<String> langues) {
        this.langues = langues;
    }

    public List<String> getOaiSets() {
        return oaiSets;
    }

    public void setOaiSets(List<String> oaiSets) {
        this.oaiSets = oaiSets;
    }

}
