package fr.abes.theses2ES.writer;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.JsonpMapper;
import fr.abes.theses2ES.dto.TheseDTO;
import jakarta.json.spi.JsonProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.io.*;
import java.util.List;

@Slf4j
public class ESItemWriter implements ItemWriter<TheseDTO> {

    @Override
    public void write(List<? extends TheseDTO> items) throws Exception {

        BulkRequest.Builder br = new BulkRequest.Builder();

        for (TheseDTO theseDTO : items) {
            JsonData json = readJson(new ByteArrayInputStream(theseDTO.getJson().getBytes()), ElasticClient.getElasticsearchClient());

            this.ecrireDansFichier(theseDTO.getJson(), theseDTO.getNnt());
            br.operations(op -> op
                    .index(idx -> idx
                            .index("theses-sample-2")
                            .id(theseDTO.getNnt() == null? theseDTO.getIdSujet() : theseDTO.getNnt())
                            .document(json)
                    )
            );
        }

        BulkResponse result = ElasticClient.getElasticsearchClient().bulk(br.build());

        if (result.errors()) {
            log.error("Erreurs dans le bulk : ");
            for (BulkResponseItem item: result.items()) {
                if (item.error() != null) {
                    log.error(item.error().reason());
                }
            }
        }
    }

    public static JsonData readJson(InputStream input, ElasticsearchClient esClient) {
        JsonpMapper jsonpMapper = esClient._transport().jsonpMapper();
        JsonProvider jsonProvider = jsonpMapper.jsonProvider();

        return JsonData.from(jsonProvider.createParser(input), jsonpMapper);
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
