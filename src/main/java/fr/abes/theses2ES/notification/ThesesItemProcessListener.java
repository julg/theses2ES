package fr.abes.theses2ES.notification;

import fr.abes.theses2ES.dto.TheseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Qualifier("theseProcessListener")
public class ThesesItemProcessListener implements ItemProcessListener<TheseDTO, TheseDTO> {

    @Override
    public void beforeProcess(TheseDTO o) {
        log.info("begin process of " + o.getIdDoc() + "...");
    }

    @Override
    public void afterProcess(TheseDTO o, TheseDTO o2) {
        log.info("... end process of " + o.getIdDoc());

    }

    @Override
    public void onProcessError(TheseDTO o, Exception e) {
        log.error("erreur lors du process de " + o.getIdDoc() + ", erreur = " + e.toString());
    }
}
