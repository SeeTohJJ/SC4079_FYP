package com.SeeTohJJ.Backend.user.service.impl;

import com.SeeTohJJ.Backend.auth.dto.RegisterRequestDTO;
import com.SeeTohJJ.Backend.auth.model.User;
import com.SeeTohJJ.Backend.user.dao.UserProfileDao;
import com.SeeTohJJ.Backend.user.model.UserProfile;
import com.SeeTohJJ.Backend.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserProfileDao userProfileDao;

    @Autowired
    public UserServiceImpl(UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    @Override
    public void setUserProfile(Long userId, RegisterRequestDTO request) {
        logger.info("Starting setUserProfile");

        UserProfile userProfile = new UserProfile();

        userProfile.setUserId(userId);
        userProfile.setUsername(request.getUsername());
        userProfile.setGender(request.getGender());
        userProfile.setAge(request.getAge());
        userProfile.setEmploymentStatus(request.getEmploymentStatus());
        userProfile.setIncome(request.getIncome());
        userProfile.setCountry(request.getCountry());

        userProfileDao.setUserProfile(userProfile);
    }
}
