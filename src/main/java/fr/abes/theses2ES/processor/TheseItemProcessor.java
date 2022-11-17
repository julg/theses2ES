package fr.abes.theses2ES.processor;

import java.io.ByteArrayInputStream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

import fr.abes.theses2ES.dto.TheseDTO;
import fr.abes.theses2ES.dto.TheseMappee;
import fr.abes.theses2ES.model.jaxb.Mets;
import fr.abes.theses2ES.utils.XMLJsonMarshalling;
@Slf4j
public class TheseItemProcessor implements ItemProcessor<TheseDTO, TheseDTO> {

    @Autowired
    private XMLJsonMarshalling marshall;

    @Override
    public TheseDTO process(TheseDTO item) throws Exception {

        log.info("debut de traitement de " + item.getNnt());
        Mets mets = marshall.chargerMets(new ByteArrayInputStream(item.getDoc().getBytes()));
        String json = new Gson().toJson(new TheseMappee(mets));
        item.setJson(json);
        return item;
    }
}
