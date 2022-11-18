package fr.abes.theses2ES.writer;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.JsonpMapper;
import fr.abes.theses2ES.dto.TheseDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Slf4j
@Component
public class ESItemWriter implements ItemWriter<TheseDTO> {

    @Autowired
    ElasticClient elasticClient;
    @Override
    public void write(List<? extends TheseDTO> items) throws Exception {

        BulkRequest.Builder br = new BulkRequest.Builder();

        for (TheseDTO theseDTO : items) {
            JsonData json = readJson(
                    new ByteArrayInputStream(theseDTO.getJson().getBytes()),
                    this.elasticClient.getElasticsearchClient());

            br.operations(op -> op
                    .index(idx -> idx
                            .index("theses-sample-2")
                            .id(theseDTO.getNnt())
                            .document(json)
                    )
            );
        }

        BulkResponse result = this.elasticClient.getElasticsearchClient().bulk(br.build());

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
        jakarta.json.spi.JsonProvider jsonProvider = jsonpMapper.jsonProvider();

        return JsonData.from(jsonProvider.createParser(input), jsonpMapper);
    }
}
