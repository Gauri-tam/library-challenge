package com.jwtAuthLibrary.jwtBookAuthor.controller;

import com.jwtAuthLibrary.jwtBookAuthor.entity.Book;
import com.jwtAuthLibrary.jwtBookAuthor.dto.PageableAndSorting;
import com.jwtAuthLibrary.jwtBookAuthor.service.BookServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/library")
@RequiredArgsConstructor
public class BookController {

    // http://localhost:8080/api/v1/library/book

    private final BookServices bookServices;

    @PostMapping("/book")
    public ResponseEntity<String> createBook(@RequestBody Book book){
        return ResponseEntity.ok(bookServices.create(book));
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book,@PathVariable("id") Integer id) {
        return ResponseEntity.ok(bookServices.update(book, id));
    }

    @GetMapping("/book")
    public Page<Book> getAllAuthor(@RequestBody PageableAndSorting sorting, String authName){
        Pageable pageable = new PageableAndSorting().getPage(sorting);
        return bookServices.findAllAuthor(authName, pageable);
    }

    @GetMapping("/book/get")
    public Page<Book> getAllAuthor(@RequestBody PageableAndSorting sorting){
        Pageable pageable = new PageableAndSorting().getPage(sorting);
        return bookServices.findAllAuthor(pageable);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(bookServices.getById(id));
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(bookServices.deleteById(id));
    }
}
