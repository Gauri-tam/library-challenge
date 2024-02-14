package com.jwtAuthLibrary.jwtBookAuthor.controller;

import com.jwtAuthLibrary.jwtBookAuthor.dto.PageableAndSorting;
import com.jwtAuthLibrary.jwtBookAuthor.entity.Author;
import com.jwtAuthLibrary.jwtBookAuthor.entity.Book;
import com.jwtAuthLibrary.jwtBookAuthor.service.AuthorServices;
import com.jwtAuthLibrary.jwtBookAuthor.service.BookServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/library/user")
public class UserController {

    /***
     * User Can Get All The Data of Author and Book
     * User can Only Read data
     */

    private final BookServices bookServices;

    private final AuthorServices authorServices;

    @GetMapping("/author")
    public ResponseEntity<Page<Author>> getAllAuthor(@RequestBody PageableAndSorting sorting, String bookName){
        Pageable pageable = new PageableAndSorting().getPage(sorting);
        return ResponseEntity.ok(authorServices.findAllAuthors(bookName, pageable));
    }

    @GetMapping("/author/get")
    public ResponseEntity<Page<Author>> getAllAuthor(@RequestBody PageableAndSorting sorting){
        Pageable pageable = new PageableAndSorting().getPage(sorting);
        return ResponseEntity.ok(authorServices.findAllAuthors(pageable));
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(authorServices.getById(id)) ;
    }

    @GetMapping("/book")
    public ResponseEntity<Page<Book>> getAllBook(@RequestBody PageableAndSorting sorting, String authName){
        Pageable pageable = new PageableAndSorting().getPage(sorting);
        return ResponseEntity.ok(bookServices.findAllAuthor(authName, pageable));
    }

    @GetMapping("/book/get")
    public ResponseEntity<Page<Book>> getAllBook(@RequestBody PageableAndSorting sorting){
        Pageable pageable = new PageableAndSorting().getPage(sorting);
        return ResponseEntity.ok(bookServices.findAllAuthor(pageable));
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(bookServices.getById(id));
    }
}
