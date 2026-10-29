package com.visualpathit.account.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

import com.visualpathit.account.model.User;
import com.visualpathit.account.service.UserService;
import com.visualpathit.account.utils.ElasticsearchUtil;

@Controller
public final class ElasticSearchController {
    /** Elasticsearch index containing user documents. */
    private static final String USERS_INDEX = "users";
    /** View used to display Elasticsearch operation results. */
    private static final String RESULT_VIEW = "elasticeSearchRes";

    /** Logger for Elasticsearch operation outcomes. */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(ElasticSearchController.class);

    /** User persistence service used to load indexed records. */
    private final UserService userService;

    /** Elasticsearch client factory. */
    private final ElasticsearchUtil elasticsearchUtil;

    /**
     * Creates the Elasticsearch controller.
     *
     * @param service user persistence service
     * @param clientFactory Elasticsearch client factory
     */
    public ElasticSearchController(
            final UserService service,
            final ElasticsearchUtil clientFactory) {
        this.userService = service;
        this.elasticsearchUtil = clientFactory;
    }

    /**
     * Indexes all users in Elasticsearch.
     *
     * @param model view model for the result
     * @return the Elasticsearch result view
     * @throws IOException if creating or using the Elasticsearch client fails
     */
    @GetMapping("/user/elasticsearch")
    public String insert(final Model model) throws IOException {
        List<User> users = userService.getList();

        try (RestHighLevelClient client =
                     elasticsearchUtil.getRestHighLevelClient()) {
            for (User user : users) {
                IndexRequest indexRequest = new IndexRequest(USERS_INDEX)
                        .id(String.valueOf(user.getId()))
                        .source(XContentFactory.jsonBuilder()
                                .startObject()
                                .field("name", user.getUsername())
                                .field("DOB", user.getDateOfBirth())
                                .field("fatherName", user.getFatherName())
                                .field("motherName", user.getMotherName())
                                .field("gender", user.getGender())
                                .field("nationality", user.getNationality())
                                .field("phoneNumber", user.getPhoneNumber())
                                .endObject());

                IndexResponse response = client.index(
                        indexRequest, RequestOptions.DEFAULT);
                LOGGER.info("Indexed user {} with result {}",
                        user.getId(), response.getResult());
            }
        }

        model.addAttribute("result", "Users indexed successfully");
        return RESULT_VIEW;
    }

    /**
     * Retrieves an indexed user's name.
     *
     * @param id Elasticsearch document identifier
     * @param model view model for the result
     * @return the Elasticsearch result view
     * @throws IOException if creating or using the Elasticsearch client fails
     */
    @GetMapping("/rest/" + USERS_INDEX + "/view/{id}")
    public String view(
            @PathVariable final String id,
            final Model model) throws IOException {
        try (RestHighLevelClient client =
                     elasticsearchUtil.getRestHighLevelClient()) {
            GetRequest getRequest = new GetRequest(USERS_INDEX, id);
            GetResponse getResponse = client.get(
                    getRequest, RequestOptions.DEFAULT);

            Map<String, Object> source = getResponse.getSource();
            model.addAttribute(
                    "res", source == null ? null : source.get("name"));
        }

        return RESULT_VIEW;
    }

    /**
     * Updates the indexed user's gender.
     *
     * @param id Elasticsearch document identifier
     * @param model view model for the result
     * @return the Elasticsearch result view
     * @throws IOException if creating or using the Elasticsearch client fails
     */
    @GetMapping("/rest/" + USERS_INDEX + "/update/{id}")
    public String update(
            @PathVariable final String id,
            final Model model) throws IOException {
        try (RestHighLevelClient client =
                     elasticsearchUtil.getRestHighLevelClient()) {
            UpdateRequest updateRequest = new UpdateRequest(USERS_INDEX, id)
                    .doc(XContentFactory.jsonBuilder()
                            .startObject()
                            .field("gender", "male")
                            .endObject());

            UpdateResponse updateResponse = client.update(
                    updateRequest, RequestOptions.DEFAULT);
            LOGGER.info("Elasticsearch update status: {}",
                    updateResponse.status());
            model.addAttribute("res", updateResponse.status());
        }

        return RESULT_VIEW;
    }

    /**
     * Deletes an indexed user.
     *
     * @param id Elasticsearch document identifier
     * @param model view model for the result
     * @return the Elasticsearch result view
     * @throws IOException if creating or using the Elasticsearch client fails
     */
    @GetMapping("/rest/" + USERS_INDEX + "/delete/{id}")
    public String delete(
            @PathVariable final String id,
            final Model model) throws IOException {
        try (RestHighLevelClient client =
                     elasticsearchUtil.getRestHighLevelClient()) {
            DeleteRequest deleteRequest = new DeleteRequest(USERS_INDEX, id);
            DeleteResponse deleteResponse = client.delete(
                    deleteRequest, RequestOptions.DEFAULT);

            String result = deleteResponse.getResult().toString();
            LOGGER.info("Elasticsearch delete result: {}", result);
            model.addAttribute("res", result);
        }

        return RESULT_VIEW;
    }
}
