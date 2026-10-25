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
public class ElasticSearchController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchController.class);

    private final UserService userService;

    public ElasticSearchController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/elasticsearch")
    public String insert(final Model model) throws IOException {
        List<User> users = userService.getList();

        try (RestHighLevelClient client = ElasticsearchUtil.getRestHighLevelClient()) {
            for (User user : users) {
                IndexRequest indexRequest = new IndexRequest("users").id(String.valueOf(user.getId()))
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

                IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
                LOGGER.info("Indexed user {} with result {}", user.getId(), response.getResult());
            }
        }

        model.addAttribute("result", "Users indexed successfully");
        return "elasticeSearchRes";
    }

    @GetMapping("/rest/users/view/{id}")
    public String view(@PathVariable final String id, final Model model) throws IOException {
        try (RestHighLevelClient client = ElasticsearchUtil.getRestHighLevelClient()) {
            GetRequest getRequest = new GetRequest("users", id);
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);

            Map<String, Object> source = getResponse.getSource();
            model.addAttribute("res", source == null ? null : source.get("name"));
        }

        return "elasticeSearchRes";
    }

    @GetMapping("/rest/users/update/{id}")
    public String update(@PathVariable final String id, final Model model) throws IOException {
        try (RestHighLevelClient client = ElasticsearchUtil.getRestHighLevelClient()) {
            UpdateRequest updateRequest = new UpdateRequest("users", id)
                    .doc(XContentFactory.jsonBuilder()
                            .startObject()
                            .field("gender", "male")
                            .endObject());

            UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
            LOGGER.info("Elasticsearch update status: {}", updateResponse.status());
            model.addAttribute("res", updateResponse.status());
        }

        return "elasticeSearchRes";
    }

    @GetMapping("/rest/users/delete/{id}")
    public String delete(@PathVariable final String id, final Model model) throws IOException {
        try (RestHighLevelClient client = ElasticsearchUtil.getRestHighLevelClient()) {
            DeleteRequest deleteRequest = new DeleteRequest("users", id);
            DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);

            String result = deleteResponse.getResult().toString();
            LOGGER.info("Elasticsearch delete result: {}", result);
            model.addAttribute("res", result);
        }

        return "elasticeSearchRes";
    }
}
