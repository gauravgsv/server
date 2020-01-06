package com.citylive.server.service;

import com.citylive.server.dao.UserRepository;
import com.citylive.server.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User addUserWithJPA(User user){
        return userRepository.save(user);
    }

    public Iterable<User> getAllUser() {
        return userRepository.findAll();
    }

    public void updateUser(User user) {
        userRepository.updateUserPassword(user.getUserName(), user.getPassword());
    }

    public Optional<User> findByUserName(String userName) {
        return userRepository.findById(userName);
    }

    public void delete(String userName) {
        userRepository.deleteById(userName);
    }
}
