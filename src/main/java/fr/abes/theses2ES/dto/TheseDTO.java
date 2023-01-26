package fr.abes.theses2ES.dto;

public class TheseDTO {

    private int idDoc;
    private String nnt;
    private String doc;
    private String json;
    private String idSujet;

    public int getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(int idDoc) {
        this.idDoc = idDoc;
    }

    public String getNnt() {
        return nnt;
    }

    public void setNnt(String nnt) {
        this.nnt = nnt;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getIdSujet() {
        return idSujet;
    }

    public void setIdSujet(String idSujet) {
        this.idSujet = idSujet;
    }

}
