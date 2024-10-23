package com.estsoft.springproject.blog.domain.dto;

import com.estsoft.springproject.blog.domain.Comment;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDTO {
    private String body;
}
