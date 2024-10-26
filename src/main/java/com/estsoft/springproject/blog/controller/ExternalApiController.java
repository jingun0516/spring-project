package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.service.BlogService;
import com.estsoft.springproject.blog.service.CommentService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
public class ExternalApiController {
    private static final Logger log = LoggerFactory.getLogger(ExternalApiController.class);

    private final BlogService service;
    private final CommentService commentService;

    public ExternalApiController(BlogService service, CommentService commentService) {
        this.service = service;
        this.commentService = commentService;
    }

    @PostMapping("/api/external")
    public String callApi() {
        List<Content> contents = fetchContentsFromExternalApi();

        if (contents != null) {
            for (Content content : contents) {
                service.saveArticle(content.toEntity());
            }
        }

        return "OK";
    }

    private List<Content> fetchContentsFromExternalApi() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://jsonplaceholder.typicode.com/posts";

        ResponseEntity<List<Content>> resultList =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Content>>() {});
        return resultList.getBody();
    }

    @PostMapping("/api/external2")
    public String callCommentApi() {
        List<CommentContent> comments = fetchCommentsFromExternalApi();

        if (comments != null) {
            for (CommentContent comment : comments) {
                commentService.saveComment(comment.getArticle_id(), comment.toEntity());
            }
        }

        return "OK";
    }

    private List<CommentContent> fetchCommentsFromExternalApi() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://jsonplaceholder.typicode.com/comments";

        ResponseEntity<List<CommentContent>> resultList =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CommentContent>>() {});
        return resultList.getBody();
    }
}
