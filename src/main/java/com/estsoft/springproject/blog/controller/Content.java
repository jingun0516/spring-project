package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.domain.dto.AddArticleRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Content {
    @JsonProperty("id")
    private long article_id;
    private String title;
    private String body;

    public AddArticleRequest toEntity() {
        AddArticleRequest request = new AddArticleRequest(article_id, title, body);

        return request;
    }

}
