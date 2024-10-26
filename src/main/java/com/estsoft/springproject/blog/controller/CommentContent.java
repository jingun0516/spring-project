package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.domain.dto.AddArticleRequest;
import com.estsoft.springproject.blog.domain.dto.CommentRequestDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CommentContent {
    @JsonProperty("postId")
    private long article_id;
    private String body;

    public CommentRequestDTO toEntity() {
        CommentRequestDTO request = new CommentRequestDTO(article_id, body);

        return request;
    }
}
