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
@RequestMapping("/api/v1/library/admin")
public class AdminController {

// http://localhost:8080/api/v1/library/admin/admin/get

    /***
     *  admin can access all the operation by followed
     *  admin can Create, Read, Update, Delete the author with the Book.
     */

    private final AuthorServices authorServices;

    private final BookServices bookServices;

    @PostMapping("/author")
    public ResponseEntity<String> createAuthor(@RequestBody Author author){
        return ResponseEntity.ok(authorServices.create(author));
    }

    @PutMapping("/author/{id}")
    public ResponseEntity<Author> updateAuthor(
            @RequestBody Author author,@PathVariable("id") Integer id){
        return ResponseEntity.ok(authorServices.update(author, id));
    }

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

    @DeleteMapping("/author/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable("id") Integer id){
        return ResponseEntity.ok(authorServices.deleteById(id));
    }
    @PostMapping("/book")
    public ResponseEntity<String> createBook(@RequestBody Book book){
        return ResponseEntity.ok(bookServices.create(book));
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book,@PathVariable("id") Integer id) {
        return ResponseEntity.ok(bookServices.update(book, id));
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

    @DeleteMapping("/book/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(bookServices.deleteById(id));
    }
}
