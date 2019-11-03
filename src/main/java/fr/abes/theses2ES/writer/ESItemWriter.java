package fr.abes.theses2ES.writer;

import fr.abes.theses2ES.dto.TheseDTO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

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
            request.source(jsonString, XContentType.JSON);
            bulkRequest.add(request);
        }
        BulkResponse bulkResponse = ElasticClient.getClient().bulk(bulkRequest, requestOptions);
        if (bulkResponse.hasFailures()) {
            int nbErrors = bulkResponse.getItems().length;
            log.error(nbErrors + " errors.");
        }
    }
}
