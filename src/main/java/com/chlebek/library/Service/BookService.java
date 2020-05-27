package com.chlebek.library.Service;

import com.chlebek.library.Model.Book;
import com.chlebek.library.Model.Form.BookForm;

import java.util.List;

public interface BookService {

    List<Book> getBooks(int page, int size);

    List<Book> getBooks(String name, int page, int size);

    List<Book> getBooksFromCategory(Long id, int page, int size);

    Book addBook(BookForm bookForm);

    Book getBook(Long id);

    Book editBook(Book editBook);

    void deleteBook(Long id);

    boolean checkIfContains(Long id);
}
