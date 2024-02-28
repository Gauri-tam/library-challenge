package com.jwtAuthLibrary.jwtBookAuthor.controller;

import com.jwtAuthLibrary.jwtBookAuthor.entity.Author;
import com.jwtAuthLibrary.jwtBookAuthor.dto.PageableAndSorting;
import com.jwtAuthLibrary.jwtBookAuthor.service.AuthorServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/library")
@RequiredArgsConstructor
public class AuthorController {

    // http://localhost:8080/api/v2/library/author

    private final AuthorServices authorServices;

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
}
