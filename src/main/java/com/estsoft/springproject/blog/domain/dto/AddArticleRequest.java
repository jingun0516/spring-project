package com.estsoft.springproject.blog.domain.dto;

import com.estsoft.springproject.blog.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddArticleRequest {
    private long article_id;
    private String title;
    private String content;

    public Article toEntity() {
        return Article.builder()
                .id(this.article_id)
                .title(this.title)
                .content(this.content)
                .build();
    }
}
