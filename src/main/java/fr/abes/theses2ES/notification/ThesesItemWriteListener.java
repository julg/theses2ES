package fr.abes.theses2ES.notification;

import fr.abes.theses2ES.dto.TheseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Qualifier("theseWriteListener")
public class ThesesItemWriteListener implements ItemWriteListener<TheseDTO> {

    @Override
    public void beforeWrite(List<? extends TheseDTO> items) {
    }
    /**
     * Ecrire le nnt dans le fichier de log
     */
    @Override
    public void afterWrite(List<? extends TheseDTO> items) {
        if (!items.isEmpty()) {
            for (TheseDTO theseDTO : items) {
                log.info("Writer OK pour " + theseDTO.getIdDoc());
            }
        }
    }

    @Override
    public void onWriteError(Exception exception, List<? extends TheseDTO> items) {
        for (TheseDTO theseDTO : items) {
            log.error("Writer KO  pour " + theseDTO.getIdDoc());
            log.error(exception.toString());
        }
    }

}
