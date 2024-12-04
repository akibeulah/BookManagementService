package com.beulah.bookmanagementservice.repository;

import com.beulah.bookmanagementservice.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByIsbn(String isbnz);

    @Query(value = "SELECT * FROM Book b " +
            "WHERE REGEXP_LIKE(LOWER(b.title), LOWER(:keyword)) " +
            "   OR REGEXP_LIKE(LOWER(b.isbn), LOWER(:keyword)) " +
            "   OR REGEXP_LIKE(LOWER(b.author), LOWER(:keyword))", nativeQuery = true)
    Page<Book> searchAllBooks(@Param("keyword") String keyword, Pageable pageable);
}
