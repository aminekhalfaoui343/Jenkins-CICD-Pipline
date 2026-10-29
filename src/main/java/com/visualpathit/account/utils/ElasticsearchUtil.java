package com.visualpathit.account.utils;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

import com.visualpathit.account.beans.Components;

@Service
public final class ElasticsearchUtil {

    /** Injected configuration used to create Elasticsearch clients. */
    private final Components components;

    /**
     * Creates the utility with injected Elasticsearch configuration.
     *
     * @param configuration application configuration bean
     */
    public ElasticsearchUtil(final Components configuration) {
        this.components = configuration;
    }

    /**
     * Creates an Elasticsearch high-level REST client from application
     * settings.
     *
     * @return configured Elasticsearch client, or {@code null} if
     *         initialization fails
     */
    public RestHighLevelClient getRestHighLevelClient() {
        System.out.println("Creating Elasticsearch client...");
        String elasticsearchHost = components.getElasticsearchHost();
        String elasticsearchPort = components.getElasticsearchPort();

        System.out.println("Elasticsearch Host: " + elasticsearchHost);
        System.out.println("Elasticsearch Port: " + elasticsearchPort);

        RestHighLevelClient client = null;
        try {
            client = new RestHighLevelClient(
                    RestClient.builder(new HttpHost(
                            elasticsearchHost,
                            Integer.parseInt(elasticsearchPort),
                            "http")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }
}
