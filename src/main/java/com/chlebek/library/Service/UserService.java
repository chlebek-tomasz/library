package com.chlebek.library.Service;

import com.chlebek.library.Model.Form.UserForm;
import com.chlebek.library.Model.User;

public interface UserService {
    User saveUser(UserForm userForm);

    void deleteUser(Long id);

    User editUser(User editUser);

    User getUser(Long id);

    User getUserByUsername(String username);

    User setUser();
}
