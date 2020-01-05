package com.citylive.server.service;

import com.citylive.server.dao.UserDao;
import com.citylive.server.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserDao userDao;

    public UserService(@Autowired final UserDao userDao){
        this.userDao = userDao;
    }

    public User addUser(User user) {
        return userDao.insertUser(user);
    }

    public List<User> getAllUser() {
        return userDao.findAll();
    }
}
