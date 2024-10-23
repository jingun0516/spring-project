package com.estsoft.springproject.book.service;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.dto.AddArticleRequest;
import com.estsoft.springproject.book.domain.Book;
import com.estsoft.springproject.book.domain.dto.BookDTO;
import com.estsoft.springproject.book.repository.BookRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    // 전체 조회
    public List<Book> findAll() {
        return repository.findAll(Sort.by("id"));   // 오름차순 정렬
    }

    // 단건 조회 id(PK) 1건
    public Book findBy(String id) {
        return repository.findById(id).orElse(new Book());
        //.orElseThrow(() -> new IllegalArgumentException("not found id: " + id));
    }

    // 저장
    public Book saveOne(Book book) {
        return repository.save(book);
    }
}
