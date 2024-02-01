package com.jwtAuthLibrary.jwtBookAuthor.service;

import com.jwtAuthLibrary.jwtBookAuthor.entity.Book;
import com.jwtAuthLibrary.jwtBookAuthor.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServices {

    private final BookRepository bookRepository;

    public String create(Book book){
        bookRepository.save(book);
        return "Created !";
    }

    public Book update(Book book, Integer id){
        if (bookRepository.existsById(id)){
            return bookRepository.save(book) ;
        }
        return null;
    }

    public List<Book> getAll(Integer pageNumber, Integer pageSize){

        Pageable pageable = PageRequest.of(pageNumber,pageSize);

        Page<Book> books = bookRepository.findAll(pageable);

        return books.getContent();
    }

    public Book getById(Integer id){
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }

    public String deleteById(Integer id){
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()){
            this.bookRepository.deleteById(id);
            return "Deleted! ";
        }
        return null;
    }

//    public List<Book> getAllSorted(String name) {
//        Sort books = Sort.by(name);
//        return bookRepository.findAll(books);
//    }

}
