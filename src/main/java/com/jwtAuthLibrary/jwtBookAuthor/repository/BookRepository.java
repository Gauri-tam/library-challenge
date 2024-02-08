package com.jwtAuthLibrary.jwtBookAuthor.repository;

import com.jwtAuthLibrary.jwtBookAuthor.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{

        // its a simple JPQL(java Persistence Query Language) Query to find all book by author name

        @Query("select a \n" +
            "    from Author a\n" +
            "    left join Book b\n" +
            "    on  b.bookId = a.authId\n" +
            "    where a.authName = :authName")
        Page<Book> findBookByAuthName(@Param("authName") String authName, Pageable pageable);
}
