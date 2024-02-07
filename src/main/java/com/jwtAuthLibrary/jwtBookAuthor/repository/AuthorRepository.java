package com.jwtAuthLibrary.jwtBookAuthor.repository;

import com.jwtAuthLibrary.jwtBookAuthor.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query("select b \n" +
            "from Book b \n" +
            "left join Author a \n" +
            "on b.bookId = a.authId  \n" +
            "where b.bookName = :bookName")
    Page<Author> findAuthorByBookName(String bookName, Pageable pageable);

}
