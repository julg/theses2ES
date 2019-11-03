package fr.abes.theses2ES.processor;

import java.io.ByteArrayInputStream;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

import fr.abes.theses2ES.dto.TheseDTO;
import fr.abes.theses2ES.dto.TheseMappee;
import fr.abes.theses2ES.model.jaxb.Mets;
import fr.abes.theses2ES.utils.XMLJsonMarshalling;

public class TheseItemProcessor implements ItemProcessor<TheseDTO, TheseDTO> {

    @Autowired
    private XMLJsonMarshalling marshall;

    @Override
    public TheseDTO process(TheseDTO item) throws Exception {

        Mets mets = marshall.chargerMets(new ByteArrayInputStream(item.getDoc().getBytes()));
        String json = new Gson().toJson(new TheseMappee(mets));
        item.setJson(json);
        return item;
    }
}
