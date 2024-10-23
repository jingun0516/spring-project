package com.estsoft.springproject.blog.service;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.Comment;
import com.estsoft.springproject.blog.domain.dto.CommentArticleResponseDTO;
import com.estsoft.springproject.blog.domain.dto.CommentRequestDTO;
import com.estsoft.springproject.blog.repository.BlogRepository;
import com.estsoft.springproject.blog.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;
//    private final BlogService blogService;

    public CommentService(BlogRepository blogRepository, CommentRepository commentRepository, BlogService blogService) {
        this.blogRepository = blogRepository;
        this.commentRepository = commentRepository;
//        this.blogService = blogService;
    }

    // 댓글 정보 생성 REST API
    public Comment saveComment(Long articleId, CommentRequestDTO request) {
//        Article article = blogService.findBy(articleId);
        Article article = blogRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("not found id: " + articleId));

        return commentRepository.save(new Comment(request.getBody(), article));
    }

    // 댓글 정보 조회 REST API
    public Comment findComment(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        return optionalComment.orElse(new Comment());

//        return commentRepository.findById(commentId)
//                .orElseThrow(() -> new IllegalArgumentException("not found id: " + commentId));
    }



    // 댓글 정보 수정 REST API
    @Transactional
    public Comment update(Long commentId, CommentRequestDTO request) {
//        Comment comment = commentRepository.findById(commentId).orElseThrow();
        Comment comment = findComment(commentId);
        comment.updateCommentBody(request.getBody());

        return commentRepository.save(comment);
    }



    // 댓글 정보 삭제 REST API
    public void deleteBy(Long commentId) {
        commentRepository.deleteById(commentId);
    }


    // 게시글과 댓글 정보를 한 번에 조회하는 REST API
    public Article gets(Long articleId) {
        Article article = blogRepository.findById(articleId).orElseThrow();
        article.setComments(commentRepository.findAll());

        return article;
    }
}
