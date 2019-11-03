package fr.abes.theses2ES.writer;

import java.util.List;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.batch.item.ItemWriter;

import fr.abes.theses2ES.dto.TheseDTO;

public class ESItemWriter implements ItemWriter<TheseDTO> {

    private RequestOptions requestOptions = RequestOptions.DEFAULT;

    @Override
    public void write(List<? extends TheseDTO> items) throws Exception {

        for (TheseDTO theseDTO : items) {
            IndexRequest request = new IndexRequest();
            request.index("theses");
            request.id(String.valueOf(theseDTO.getIdDoc()));
            String jsonString = theseDTO.getJson();
            request.source(jsonString, XContentType.JSON);
            ElasticClient.getClient().index(request, requestOptions);
        }
    }

}
