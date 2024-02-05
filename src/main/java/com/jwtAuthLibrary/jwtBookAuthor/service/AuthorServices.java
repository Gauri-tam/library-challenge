package com.jwtAuthLibrary.jwtBookAuthor.service;

import com.jwtAuthLibrary.jwtBookAuthor.entity.Author;
import com.jwtAuthLibrary.jwtBookAuthor.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServices {

    // To access All the Repository methods
    private final AuthorRepository authorRepository;

    //to save data in database
    public String create(Author author){
        authorRepository.save(author);
        return "Created !";
    }

    //to update data by using id
    public Author update(Author author, Integer id){
        if (authorRepository.existsById(id)){
           return this.authorRepository.save(author);
        }
        return null;
    }

   // pageable and Sorting without any parameter
    public Page<Author> findAllBook(Pageable pageable){
        return authorRepository.findAll(pageable);
    }

    // to get data by using id
    public Author getById(Integer id){
        Optional<Author> author = authorRepository.findById(id);
        return author.orElse(null);
    }

    //delete data by using id
    public String deleteById(Integer id){
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()){
            authorRepository.deleteById(id);
            return "Deleted! ";
        }
        return null;
    }
}
