package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.domain.dto.AddArticleRequest;
import lombok.Getter;

@Getter
public class Content {
    private String title;
    private String body;

    public AddArticleRequest toEntity() {
        AddArticleRequest request = new AddArticleRequest(title, body);

        return request;
    }

}
