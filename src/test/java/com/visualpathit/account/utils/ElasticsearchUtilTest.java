package com.visualpathit.account.utils;

import static org.junit.Assert.assertNotNull;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

import com.visualpathit.account.beans.Components;

public class ElasticsearchUtilTest {
    @Test
    public void createsClientFromConfiguredEndpoint() throws Exception {
        Components components = new Components();
        components.setElasticsearchHost("localhost");
        components.setElasticsearchPort("9200");

        try (RestHighLevelClient client =
                     new ElasticsearchUtil(components)
                             .getRestHighLevelClient()) {
            assertNotNull(client);
        }
    }
}
