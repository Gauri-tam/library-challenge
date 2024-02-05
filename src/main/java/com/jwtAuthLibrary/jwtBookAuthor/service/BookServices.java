package com.jwtAuthLibrary.jwtBookAuthor.service;

import com.jwtAuthLibrary.jwtBookAuthor.entity.Book;
import com.jwtAuthLibrary.jwtBookAuthor.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServices {

    // To access All the Repository methods
    private final BookRepository bookRepository;

    //to save data in database
    public String create(Book book){
        bookRepository.save(book);
        return "Created !";
    }

    //to update data by using id
    public Book update(Book book, Integer id){
        if (bookRepository.existsById(id)){
            return bookRepository.save(book) ;
        }
        return null;
    }

    //
    public Page<Book> findAllAuthor(Pageable pageable){
        return bookRepository.findAll(pageable);
    }

    // to get data by using id
    public Book getById(Integer id){
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }

    //delete data by using id
    public String deleteById(Integer id){
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()){
            this.bookRepository.deleteById(id);
            return "Deleted! ";
        }
        return null;
    }
}
