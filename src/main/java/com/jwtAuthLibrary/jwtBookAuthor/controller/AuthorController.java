package com.jwtAuthLibrary.jwtBookAuthor.controller;

import com.jwtAuthLibrary.jwtBookAuthor.entity.Author;
import com.jwtAuthLibrary.jwtBookAuthor.pagesort.PageableAndSorting;
import com.jwtAuthLibrary.jwtBookAuthor.service.AuthorServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/author") // without parameter new method
    public Page<Author> getAllAuthor(@RequestBody PageableAndSorting sorting){
        Pageable pageable = new PageableAndSorting().getPage(sorting);
        return authorServices.findAllBook(pageable);
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
