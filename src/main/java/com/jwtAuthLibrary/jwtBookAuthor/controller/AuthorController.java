package com.jwtAuthLibrary.jwtBookAuthor.controller;

import com.jwtAuthLibrary.jwtBookAuthor.entity.Author;
import com.jwtAuthLibrary.jwtBookAuthor.service.AuthorServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/library")
@RequiredArgsConstructor
public class AuthorController {

    // http://localhost:8080/api/v1/library/delete/{id}

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
    public ResponseEntity<List<Author>> getAllAuthor(
            @RequestParam( value = "pageNumber", defaultValue = "0", required = false)Integer pageNumber,
            @RequestParam( value = "pageSize", defaultValue = "10", required = false)Integer pageSize ) {

        List<Author> authors = authorServices.getAll(pageNumber, pageSize);

        return new ResponseEntity<>(authors, HttpStatus.ACCEPTED);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") Integer id){
       return ResponseEntity.ok(authorServices.getById(id)) ;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable("id") Integer id){
        return ResponseEntity.ok(authorServices.deleteById(id));
    }
}
