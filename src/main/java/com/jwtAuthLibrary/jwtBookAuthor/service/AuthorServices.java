package com.jwtAuthLibrary.jwtBookAuthor.service;

import com.jwtAuthLibrary.jwtBookAuthor.entity.Author;
import com.jwtAuthLibrary.jwtBookAuthor.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServices {

    private final AuthorRepository authorRepository;

    public String create(Author author){
        authorRepository.save(author);
        return "Created !";
    }

    public Author update(Author author, Integer id){
        if (authorRepository.existsById(id)){
           return this.authorRepository.save(author);
        }
        return null;
    }

    public List<Author> getAll(Integer pageNumber , Integer pageSize){

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Author> authors = authorRepository.findAll(pageable);

        return authors.getContent();
    }

    public Author getById(Integer id){
        Optional<Author> author = authorRepository.findById(id);
        return author.orElse(null);
    }

    public String deleteById(Integer id){
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()){
            authorRepository.deleteById(id);
            return "Deleted! ";
        }
        return null;
    }
}
