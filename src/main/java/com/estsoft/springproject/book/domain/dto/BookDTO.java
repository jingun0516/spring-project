package com.estsoft.springproject.book.domain.dto;

import com.estsoft.springproject.book.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private String id;
    private String name;
    private String author;
}
