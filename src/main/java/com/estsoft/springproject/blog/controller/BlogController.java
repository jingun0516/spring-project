package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.domain.dto.AddArticleRequest;
import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.dto.ArticleResponse;
import com.estsoft.springproject.blog.domain.dto.UpdateArticleRequest;
import com.estsoft.springproject.blog.service.BlogExternalService;
import com.estsoft.springproject.blog.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "블로그 저장/수정/삭제/조회용 API")
@RestController
@RequestMapping("/api")
public class BlogController {
    private final BlogService service;

    public BlogController(BlogService service, BlogExternalService externalService) {
        this.service = service;
    }

    //    @RequestMapping(method = RequestMethod.POST, value = "/articles")
    @Operation(summary = "블로그 글 작성", description = "블로그에 새 글 작성하고 게시")
    @PostMapping("/articles")
    public ResponseEntity<ArticleResponse> writeArticle(@RequestBody AddArticleRequest request) {
        log.info("request: {}, {}", request.getTitle(), request.getContent());
        Article article = service.saveArticle(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(article.convert());
    }

    @ApiResponse(
            responseCode = "100",
            description = "요청 성공",
            content = @Content(mediaType = "application/json")
    )
    @Operation(summary = "블로그 전체 목록 보기", description = "블로그 메인 화면에서 보여주는 전체 목록")
    @GetMapping("/articles")
    public ResponseEntity<List<ArticleResponse>> findAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // List<Article> -> List<ArticleResponse> 변환해서 응답으로 보내기
        List<ArticleResponse> list = service.findAll().stream()
                .map(Article::convert)
                .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "게시글 조회")
    @GetMapping("/articles/{id}")
    @Parameter(name = "id", description = "조회할 블로그 글 ID", example = "45")
    public ResponseEntity<ArticleResponse> findById(@PathVariable Long id) {
        Article article = service.findBy(id);   // Exception (5xx server error) -> 4xx Status Code

        // Article -> ArticleResponse
        return ResponseEntity.ok(article.convert());
    }

    //    @RequestMapping(method = RequestMethod.DELETE, value = "/articles/{id}")
    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/articles/{id}")
    @Parameter(name = "id", description = "삭제할 블로그 글 ID", example = "45")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteBy(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/articles")
    public ResponseEntity<Void> deletedAll() {
        service.deleteAll();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/articles/{id}")
    @Parameter(name = "id", description = "수정할 블로그 글 ID", example = "45")
    public ResponseEntity<ArticleResponse> updateById(@PathVariable Long id,
                                                      @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = service.update(id, request);
        return ResponseEntity.ok(updatedArticle.convert());
    }


    // reference : https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-exceptionhandler.html
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // reason : ""
//    }
}