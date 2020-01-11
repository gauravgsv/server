package com.citylive.server.service;

import com.citylive.server.dao.UserLocationRepository;
import com.citylive.server.dao.UserRepository;
import com.citylive.server.dao.UserRoleRepository;
import com.citylive.server.domain.User;
import com.citylive.server.domain.UserLocation;
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

    @Autowired
    private UserLocationRepository userLocationRepository;

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

    public void updatePassword(User user) {
        userRepository.updateUserPassword(user.getUserName(), user.getPassword());
    }

    public void updateDeviceId(String userName, String deviceId) {
        userRepository.updateUserDeviceId(userName, deviceId);
    }

    public Optional<User> findByUserName(String userName) {
        return userRepository.findById(userName);
    }

    public void delete(String userName) {
        //TODO Set enabled = false, instead of delete
        userRoleRepository.deleteById(userName);
        userRepository.deleteById(userName);
    }

    public Optional<UserLocation> getUserLocation(final String userName) {
        return userLocationRepository.findById(userName);
    }

    public UserLocation updateUserLocation(UserLocation userLocation) {
        Optional<UserLocation> userLocationOptional = userLocationRepository.findById(userLocation.getUserName());
        if(userLocationOptional.isPresent()) {
            userLocationRepository.updateUserLocation(userLocation.getUserName(), userLocation.getLatitude(), userLocation.getLongitude());
            return userLocationRepository.findById(userLocation.getUserName()).get();
        } else {
            return userLocationRepository.save(userLocation.toBuilder().locationType("CURRENT").build());
        }
    }
}
