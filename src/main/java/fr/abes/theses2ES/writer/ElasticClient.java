package fr.abes.theses2ES.writer;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class ElasticClient {

    private static RestHighLevelClient client;

    private ElasticClient() {
    }

    public static RestHighLevelClient getClient() {
        return client;
    }

    public static void loadClient(String hostname, int port, String scheme) {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost(hostname, port, scheme)));
    }

}
