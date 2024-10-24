package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.service.BlogService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
public class ExternalApiController {
    private static final Logger log = LoggerFactory.getLogger(ExternalApiController.class);

    private final BlogService service;

    public ExternalApiController(BlogService service) {
        this.service = service;
    }

    @GetMapping("/api/external")
    public String callApi() {
        // 외부 API 호출
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://jsonplaceholder.typicode.com/posts";

//        ResponseEntity<String> json = restTemplate.getForEntity(url, String.class);
//
//        log.info("StatusCode: {}", json.getStatusCode());
//        log.info(json.getBody());

        // 외부 API 호출, 역직렬화 (restTemplate)
        ResponseEntity<List<Content>> resultList =
            restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Content>>() {});

//        log.info("code: {}", resultList.getStatusCode());
//        log.info("{}", resultList.getBody());

        List<Content> contents = resultList.getBody();

        String postUrl = "https://localhost:8080/articles";

        if(contents!=null) {
            for (Content content : contents) {
                service.saveArticle(content.toEntity());
            }
        }

        return "OK";
    }


    @GetMapping("/api/external2")
    public String callCommentApi() {
        // 외부 API 호출
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://jsonplaceholder.typicode.com/comments";

        // 외부 API 호출, 역직렬화 (restTemplate)
        ResponseEntity<List<Content>> resultList =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Content>>() {});

        List<Content> contents = resultList.getBody();

        String postUrl = "https://localhost:8080/articles";

        if(contents!=null) {
            for (Content content : contents) {
                service.saveArticle(content.toEntity());
            }
        }

        return "OK";
    }
}
