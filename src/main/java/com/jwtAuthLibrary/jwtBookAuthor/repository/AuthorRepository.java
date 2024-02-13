package com.jwtAuthLibrary.jwtBookAuthor.repository;

import com.jwtAuthLibrary.jwtBookAuthor.entity.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    // This is the native Query to find the bookName in our data base
    // And it get all author with the book named given bookName

    @Query(value = "select *\n" +
            "from author a \n" +
            "left join author_book ab on a.auth_id = ab.author_id\n" +
            "left join book b on  b.book_id = ab.book_id\n" +
            "where b.book_name = :bookName", nativeQuery = true)
    Page<Author> findAuthorByBookName(@Param("bookName") String bookName, Pageable pageable);
}