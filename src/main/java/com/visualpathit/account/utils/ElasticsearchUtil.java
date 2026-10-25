package com.visualpathit.account.utils;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visualpathit.account.beans.Components;

@Service
public final class ElasticsearchUtil {

    /** Injected configuration used to create Elasticsearch clients. */
    private static Components object;

    /**
     * Sets the injected Elasticsearch configuration.
     *
     * @param configuration application configuration bean
     */
    @Autowired
    public void setComponents(final Components configuration) {
        ElasticsearchUtil.object = configuration;
    }

    /**
     * Creates an Elasticsearch high-level REST client from application
     * settings.
     *
     * @return configured Elasticsearch client, or {@code null} if
     *         initialization fails
     */
    public static RestHighLevelClient getRestHighLevelClient() {
        System.out.println("Creating Elasticsearch client...");
        String elasticsearchHost = object.getElasticsearchHost();
        String elasticsearchPort = object.getElasticsearchPort();

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
