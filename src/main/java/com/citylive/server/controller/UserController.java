package com.citylive.server.controller;

import com.citylive.server.domain.User;
import com.citylive.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    public UserController(@Autowired final UserService userService){
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public User addUpdateUser(@RequestBody @Validated User user){
        return userService.addUser(user);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/all")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }
}
