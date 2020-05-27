package com.chlebek.library.Service;

import com.chlebek.library.Model.Form.UserForm;
import com.chlebek.library.Model.Role;
import com.chlebek.library.Model.User;
import com.chlebek.library.Repository.RoleRepository;
import com.chlebek.library.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public User saveUser(UserForm userForm) {
        User user = new User();
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setEmail(userForm.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword()));
        user.setUsername(userForm.getUsername());
        user.getRoles().add(roleRepository.getRoleByName("USER"));
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User editUser(User editUser) {
        User user = userRepository.getOne(editUser.getId());
        user.setUsername(editUser.getUsername());
        user.setFirstName(editUser.getFirstName());
        user.setLastName(editUser.getLastName());
        user.setPassword(editUser.getPassword());
        return userRepository.save(user);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public User setUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return getUserByUsername(userDetails.getUsername());
    }
}
