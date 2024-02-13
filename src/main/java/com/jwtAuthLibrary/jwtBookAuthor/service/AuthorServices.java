package com.jwtAuthLibrary.jwtBookAuthor.service;

import com.jwtAuthLibrary.jwtBookAuthor.entity.Author;
import com.jwtAuthLibrary.jwtBookAuthor.entity.Book;
import com.jwtAuthLibrary.jwtBookAuthor.repository.AuthorRepository;
import com.jwtAuthLibrary.jwtBookAuthor.repository.BookRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServices {

    // To access All the Repository methods
    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    //To save data in database
    public String create(Author author){
        authorRepository.save(author);
        return "Created !";
    }

    //To update data by using id
    public Author update(Author author, Integer id){
        if (authorRepository.existsById(id)){
           return this.authorRepository.save(author);
        }
        return null;
    }

    // To sorting by bookName
    public Page<Author> findAllAuthors(String bookName, Pageable pageable){
      List<Book> books = bookRepository.existsByBooksByBookName(bookName);
        for (Book book : books) {
            String singleBook = book.getBookName();
            return authorRepository.findAuthorByBookName( singleBook, pageable);
        }
        return Page.empty();
    }

    // To get all data
    public Page<Author> findAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    // To get data by using id
    public Author getById(Integer id){
        Optional<Author> author = authorRepository.findById(id);
        return author.orElse(null);
    }

    // To delete data by using id
    public String deleteById(Integer id){
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()){
            authorRepository.deleteById(id);
            return "Deleted! ";
        }
        return null;
    }
}
