package com.chlebek.library.Controller;

import com.chlebek.library.Controller.Dto.BookDto;
import com.chlebek.library.Exception.RequestException;
import com.chlebek.library.Model.Book;
import com.chlebek.library.Repository.CategoryRepository;
import com.chlebek.library.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/books")
    public List<BookDto> getBooks(@RequestParam(name = "page", required = false)Integer page,
                                  @RequestParam(name = "size", required = false)Integer size,
                                  @RequestParam(name = "name", required = false)String name){
        int pageNumber = page != null && page > 0 ? page : 0;
        int sizeNumber = size != null && size > 0 ? size : 20;
        if(name != null){
            return BookDtoMapper.mapToBookDtos(bookService.getBooks(name, pageNumber, sizeNumber));
        } else {
            return BookDtoMapper.mapToBookDtos(bookService.getBooks(pageNumber, sizeNumber));
        }
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id){
        if(bookService.checkIfContains(id)){
            return ResponseEntity.ok().body(bookService.getBook(id));
        } else {
            throw new RequestException("The book about this id does not exist");
        }
    }

    @GetMapping("/books/categories/{id}")
    public List<Book> getBooks(@PathVariable(name = "id") Long id,
                               @RequestParam(name = "page", required = false)Integer page,
                               @RequestParam(name = "size", required = false)Integer size) {
        int pageNumber = page != null && page > 0 ? page : 0;
        int sizeNumber = size != null && size > 0 ? size : 20;
        if (categoryRepository.existsById(id)) {
            return bookService.getBooksFromCategory(id, pageNumber, sizeNumber);
        } else {
            return bookService.getBooks(pageNumber, sizeNumber);
        }
    }
}
