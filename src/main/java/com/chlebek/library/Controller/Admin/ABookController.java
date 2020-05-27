package com.chlebek.library.Controller.Admin;

import com.chlebek.library.Controller.BookDtoMapper;
import com.chlebek.library.Controller.Dto.BookDto;
import com.chlebek.library.Exception.RequestException;
import com.chlebek.library.Model.Book;
import com.chlebek.library.Model.Form.BookForm;
import com.chlebek.library.Repository.CategoryRepository;
import com.chlebek.library.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class ABookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/books")
    public List<Book> getBooksAdmin(@RequestParam(name = "page", required = false)Integer page,
                                  @RequestParam(name = "size", required = false)Integer size,
                                  @RequestParam(name = "name", required = false)String name){
        int pageNumber = page != null && page > 0 ? page : 0;
        int sizeNumber = size != null && size > 0 ? size : 20;
        if(name != null){
            return bookService.getBooks(name, pageNumber, sizeNumber);
        } else {
            return bookService.getBooks(pageNumber, sizeNumber);
        }
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookAdmin(@PathVariable Long id){
        if(bookService.checkIfContains(id)){
            return ResponseEntity.ok().body(bookService.getBook(id));
        } else {
            throw new RequestException("The book about this id does not exist");
        }
    }

    @GetMapping("/books/categories/{id}")
    public List<Book> getBooksAdmin(@PathVariable(name = "id") Long id,
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

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody BookForm bookForm){
        return ResponseEntity.ok().body(bookService.addBook(bookForm));
    }

    @PutMapping("/books")
    public ResponseEntity<Book> editBook(@RequestBody Book editBook){
        return ResponseEntity.ok().body(bookService.editBook(editBook));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id){
        if(bookService.checkIfContains(id)){
            bookService.deleteBook(id);
            return ResponseEntity.ok().body("Book succesfully deleted!");
        } else {
            throw new RequestException("The book about this id does not exist");
        }
    }
}
