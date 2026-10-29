package com.visualpathit.account.controllerTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import com.visualpathit.account.controller.ElasticSearchController;
import com.visualpathit.account.model.User;
import com.visualpathit.account.service.UserService;
import com.visualpathit.account.utils.ElasticsearchUtil;

public class ElasticSearchControllerTest {
    private UserService userService;
    private ElasticsearchUtil elasticsearchUtil;
    private RestHighLevelClient client;
    private ElasticSearchController controller;
    private Model model;

    @Before
    public void setUp() throws Exception {
        userService = mock(UserService.class);
        elasticsearchUtil = mock(ElasticsearchUtil.class);
        client = mock(RestHighLevelClient.class);
        model = mock(Model.class);
        when(elasticsearchUtil.getRestHighLevelClient()).thenReturn(client);
        controller =
                new ElasticSearchController(userService, elasticsearchUtil);
    }

    @Test
    public void indexesUsers() throws Exception {
        User user = new User();
        user.setId(7L);
        user.setUsername("sample");
        when(userService.getList()).thenReturn(List.of(user));
        IndexResponse response = mock(IndexResponse.class);
        when(response.getResult()).thenReturn(DocWriteResponse.Result.CREATED);
        when(client.index(any(IndexRequest.class), eq(RequestOptions.DEFAULT)))
                .thenReturn(response);

        assertEquals(
                "elasticeSearchRes", controller.insert(model));

        verify(client).index(any(IndexRequest.class), eq(RequestOptions.DEFAULT));
        verify(model).addAttribute("result", "Users indexed successfully");
    }

    @Test
    public void returnsNameOfRetrievedDocument() throws Exception {
        GetResponse response = mock(GetResponse.class);
        when(response.getSource()).thenReturn(Map.of("name", "sample"));
        when(client.get(any(GetRequest.class), eq(RequestOptions.DEFAULT)))
                .thenReturn(response);

        assertEquals("elasticeSearchRes", controller.view("7", model));

        verify(model).addAttribute("res", "sample");
    }

    @Test
    public void updatesDocumentAndReturnsStatus() throws Exception {
        UpdateResponse response = mock(UpdateResponse.class);
        when(response.status()).thenReturn(RestStatus.OK);
        when(client.update(any(UpdateRequest.class), eq(RequestOptions.DEFAULT)))
                .thenReturn(response);

        assertEquals("elasticeSearchRes", controller.update("7", model));

        verify(model).addAttribute("res", RestStatus.OK);
    }

    @Test
    public void deletesDocumentAndReturnsResult() throws Exception {
        DeleteResponse response = mock(DeleteResponse.class);
        when(response.getResult()).thenReturn(DocWriteResponse.Result.DELETED);
        when(client.delete(any(DeleteRequest.class), eq(RequestOptions.DEFAULT)))
                .thenReturn(response);

        assertEquals("elasticeSearchRes", controller.delete("7", model));

        verify(model).addAttribute("res", "DELETED");
    }
}
