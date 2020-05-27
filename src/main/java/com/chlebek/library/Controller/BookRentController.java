package com.chlebek.library.Controller;

import com.chlebek.library.Exception.RequestException;
import com.chlebek.library.Model.Book;
import com.chlebek.library.Model.User;
import com.chlebek.library.Service.BookService;
import com.chlebek.library.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookRentController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    @PutMapping("/books/{id}/rent")
    public ResponseEntity<User> rentBook(@PathVariable Long id) {
        User user = userService.setUser();
        if (bookService.checkIfContains(id)) {
            if (user.getRentedBooks().size() < 3) {
                Book book = bookService.getBook(id);
                user.getRentedBooks().add(book);
                return ResponseEntity.ok().body(userService.editUser(user));
            } else {
                throw new RequestException("Too much rented books");
            }
        } else {
            throw new RequestException("Book with this id do not exists.");
        }
    }

    @PutMapping("/books/{id}/return")
    public ResponseEntity<User> returnBook(@PathVariable Long id){
        User user = userService.setUser();
        Book book = bookService.getBook(id);
        if(user.getRentedBooks().contains(book)){
            user.getRentedBooks().remove(book);
            return ResponseEntity.ok().body(userService.editUser(user));
        } else {
            throw new RequestException("User do not rent book with this id");
        }

    }
}
