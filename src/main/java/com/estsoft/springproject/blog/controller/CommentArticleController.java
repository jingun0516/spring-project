package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.Comment;
import com.estsoft.springproject.blog.domain.dto.CommentArticleResponseDTO;
import com.estsoft.springproject.blog.domain.dto.CommentRequestDTO;
import com.estsoft.springproject.blog.domain.dto.CommentResponseDTO;
import com.estsoft.springproject.blog.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class CommentArticleController {
    private final CommentService service;

    public CommentArticleController(CommentService service) {
        this.service = service;
    }

    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentResponseDTO> saveCommentByArticleId(@PathVariable Long articleId, @RequestBody CommentRequestDTO request) {
        Comment comment = service.saveComment(articleId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CommentResponseDTO(comment));
    }

    @GetMapping("/api/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> selectCommentById(@PathVariable("commentId") Long commentId) {
        Comment comment = service.findComment(commentId);

        return ResponseEntity.ok(new CommentResponseDTO(comment));
    }

    @PutMapping("/api/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> putComment(@PathVariable Long commentId,
                                                         @RequestBody CommentRequestDTO request) {
        Comment updatedComment = service.update(commentId, request);
        return ResponseEntity.ok(new CommentResponseDTO(updatedComment));
    }

    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId){
        service.deleteBy(commentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentArticleResponseDTO> gets(@PathVariable Long articleId) {
        Article article = service.gets(articleId);

        return ResponseEntity.ok(new CommentArticleResponseDTO(article));
    }
}
