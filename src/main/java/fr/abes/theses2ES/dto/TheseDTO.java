package fr.abes.theses2ES.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TheseDTO {

    private int idDoc;
    private String nnt;
    private String doc;
    private String json;

}
