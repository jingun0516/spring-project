package com.estsoft.springproject.book.controller;

import org.springframework.ui.Model;
import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.dto.ArticleViewResponse;
import com.estsoft.springproject.book.domain.Book;
import com.estsoft.springproject.book.domain.dto.BookDTO;
import com.estsoft.springproject.book.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    // 책 전체 조회
    @GetMapping()
    public String showAll(Model model) {
        List<BookDTO> list = service.findAll().stream().map(Book::convert).toList();

        model.addAttribute("bookList", list);

        return "bookManagement";
    }


    // 책 단권 조회
    @GetMapping("/{id}")
    public String showOne(@PathVariable String id, Model model) {
        Book book = service.findBy(id);

        model.addAttribute("book", book.convert());

        return "bookDetail";
    }

    // 책 생성
    @PostMapping()
    public String addBook(@RequestBody String id,
                          @RequestBody String name,
                          @RequestBody String author) {

        service.saveOne(new Book(id, name, author));

        //return showAll(model);
        return "redirect:/books";
    }
}
