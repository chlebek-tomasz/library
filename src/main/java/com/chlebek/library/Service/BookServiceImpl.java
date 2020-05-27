package com.chlebek.library.Service;

import com.chlebek.library.Model.Book;
import com.chlebek.library.Model.Form.BookForm;
import com.chlebek.library.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getBooks(int page, int size) {
        return bookRepository.findAllBook(PageRequest.of(page, size));
    }

    @Override
    public List<Book> getBooks(String name, int page, int size) {
        return bookRepository.findBooksByTitleContainingIgnoreCase(name, PageRequest.of(page, size));
    }

    @Override
    public List<Book> getBooksFromCategory(Long id, int page, int size) {
        return bookRepository.findAllByCategory_Id(id, PageRequest.of(page, size));
    }

    @Override
    @Transactional
    public Book addBook(BookForm book) {
        Book newBook = new Book();
        newBook.setTitle(book.getTitle());
        newBook.setIsbn(book.getIsbn());
        newBook.setPublishingYear(book.getPublishingYear());
        newBook.setDescription(book.getDescription());
        return bookRepository.save(newBook);
    }

    @Override
    public Book getBook(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Book editBook(Book book) {
        Book editBook = bookRepository.findById(book.getId()).orElseThrow();
        editBook.setAuthor(book.getAuthor());
        editBook.setCategory(book.getCategory());
        editBook.setDescription(book.getDescription());
        editBook.setIsbn(book.getIsbn());
        editBook.setPublishingHouse(book.getPublishingHouse());
        editBook.setPublishingYear(book.getPublishingYear());
        editBook.setTitle(book.getTitle());
        return bookRepository.save(editBook);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public boolean checkIfContains(Long id) {
        return bookRepository.existsById(id);
    }


}
