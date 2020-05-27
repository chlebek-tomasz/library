package com.chlebek.library.Controller;

import com.chlebek.library.Exception.RequestException;
import com.chlebek.library.Model.Form.UserForm;
import com.chlebek.library.Model.User;
import com.chlebek.library.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserForm userForm){
        if(userForm.getPassword().equals(userForm.getPasswordMatches())){
            return ResponseEntity.ok().body(userService.saveUser(userForm));
        } else {
            throw new RequestException("Passwords do not match!");
        }
    }
}
