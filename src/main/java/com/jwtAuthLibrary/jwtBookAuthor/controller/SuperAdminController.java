package com.jwtAuthLibrary.jwtBookAuthor.controller;

import com.jwtAuthLibrary.jwtBookAuthor.dto.PageableAndSorting;
import com.jwtAuthLibrary.jwtBookAuthor.dto.UserRegisterRequest;
import com.jwtAuthLibrary.jwtBookAuthor.dto.UserRegisterResponse;
import com.jwtAuthLibrary.jwtBookAuthor.entity.Author;
import com.jwtAuthLibrary.jwtBookAuthor.entity.Book;
import com.jwtAuthLibrary.jwtBookAuthor.service.AuthorServices;
import com.jwtAuthLibrary.jwtBookAuthor.service.BookServices;
import com.jwtAuthLibrary.jwtBookAuthor.service.SuperAdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/library/super-admin")
public class SuperAdminController {

    /***
     * super-admin can access all the operation like
     * Create, Read, Update, Delete
     * Admin also it can Create Admin
     */

    private final AuthorServices authorServices;

    private final BookServices bookServices;

    private final SuperAdminService superAdminService;

    // Use to Register the Admin
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> registration(@RequestBody UserRegisterRequest request, HttpServletRequest req) {
        return ResponseEntity.ok(superAdminService.registerAdmin(request, req ));
    }

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
    public Page<Author> getAllAuthor(@RequestBody PageableAndSorting sorting, String bookName){
        Pageable pageable = new PageableAndSorting().getPage(sorting);
        return authorServices.findAllAuthors(bookName, pageable);
    }

    @GetMapping("/author/get")
    public Page<Author> getAllAuthor(@RequestBody PageableAndSorting sorting){
        Pageable pageable = new PageableAndSorting().getPage(sorting);
        return authorServices.findAllAuthors(pageable);
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
    public Page<Book> getAllBook(@RequestBody PageableAndSorting sorting, String authName){
        Pageable pageable = new PageableAndSorting().getPage(sorting);
        return bookServices.findAllAuthor(authName, pageable);
    }

    @GetMapping("/book/get")
    public Page<Book> getAllBook(@RequestBody PageableAndSorting sorting){
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
