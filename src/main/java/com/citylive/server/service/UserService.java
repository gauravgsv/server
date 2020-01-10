package com.citylive.server.service;

import com.citylive.server.dao.UserRepository;
import com.citylive.server.dao.UserRoleRepository;
import com.citylive.server.domain.User;
import com.citylive.server.domain.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Transactional
    public User addUser(User user) {
        user.setEnabled(true);
        User updatedUser = userRepository.save(user);
        userRoleRepository.save(UserRole.builder().userName(updatedUser.getUserName()).role("ROLE_USER").build());
        return updatedUser;
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
        userRoleRepository.deleteById(userName);
        userRepository.deleteById(userName);
    }
}
