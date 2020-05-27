package com.chlebek.library.Repository;

import com.chlebek.library.Model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b")
    List<Book> findAllBook(Pageable pageable);

    List<Book> findBooksByTitleContainingIgnoreCase(String name, Pageable pageable);

    List<Book> findAllByCategory_Id(Long id, Pageable pageable);
}
