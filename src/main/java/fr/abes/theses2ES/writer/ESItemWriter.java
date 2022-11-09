package fr.abes.theses2ES.writer;

import fr.abes.theses2ES.dto.TheseDTO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.batch.item.ItemWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ESItemWriter implements ItemWriter<TheseDTO> {

    @Override
    public void write(List<? extends TheseDTO> items) throws Exception {

        BulkRequest bulkRequest = new BulkRequest();
        RequestOptions requestOptions = RequestOptions.DEFAULT;

        for (TheseDTO theseDTO : items) {
            IndexRequest request = new IndexRequest();
            request.id(String.valueOf(theseDTO.getIdDoc()));
            request.index("theses");
            String jsonString = theseDTO.getJson();
            log.info("AAAA" + jsonString);
            ecrireDansFichier(jsonString, theseDTO.getNnt());
            request.source(jsonString, XContentType.JSON);
            bulkRequest.add(request);
        }
        BulkResponse bulkResponse = ElasticClient.getClient().bulk(bulkRequest, requestOptions);
        if (bulkResponse.hasFailures()) {
            int nbErrors = bulkResponse.getItems().length;
            log.error(nbErrors + " errors.");
        }
    }
    private void ecrireDansFichier (String notice, String nnt) {
        try {

            File file = new File("C:\\projets\\refonteThesesFr\\tefs_2022-05-23_11-40-23\\toutes.txt");

            // créer le fichier s'il n'existe pas
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("{ \"index\": { \"_id\":\""+ nnt + "\" } },");
            bw.newLine();
            bw.write(notice);
            bw.newLine();
            bw.write(",");
            bw.newLine();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
