package com.SeeTohJJ.Backend.auth.service.impl;


import com.SeeTohJJ.Backend.auth.dao.UserDao;
import com.SeeTohJJ.Backend.auth.dto.AuthResponseDTO;
import com.SeeTohJJ.Backend.auth.dto.LoginRequestDTO;
import com.SeeTohJJ.Backend.auth.dto.RegisterRequestDTO;
import com.SeeTohJJ.Backend.auth.model.User;
import com.SeeTohJJ.Backend.auth.service.AuthService;
import com.SeeTohJJ.Backend.auth.service.JwtService;
import com.SeeTohJJ.Backend.topic.model.Topic;
import com.SeeTohJJ.Backend.topic.service.TopicService;
import com.SeeTohJJ.Backend.user.model.UserProfile;
import com.SeeTohJJ.Backend.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserDao userDao;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final TopicService topicService;
    private final UserService userService;

    @Autowired
    public AuthServiceImpl(UserDao userDao,  JwtService jwtService,  PasswordEncoder passwordEncoder,  TopicService topicService,  UserService userService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.topicService = topicService;
        this.userService = userService;
    }

    @Transactional
    public String register(RegisterRequestDTO request) {
        logger.info("Starting Register");

        if (userDao.findEmailExist(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPublicUserId(UUID.randomUUID());
        user.setRole(User.Role.USER);
        user.setCreatedAt(LocalDateTime.now());

        Long userId = userDao.registerUser(user);

        userService.setUserProfile(userId, request);

        for (String topicId : request.getTopics()) {
            topicService.setUserTopicInterest(userId, topicId);
        }

        return "User registered";
    }

    @Transactional
    public AuthResponseDTO login(LoginRequestDTO request) {
        logger.info("Starting Login");

        User user = userDao.findUserByEmail(request.getEmail());

        if  (user == null) {
            throw new RuntimeException("Invalid email or password");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        AuthResponseDTO response = new AuthResponseDTO();
        response.setToken(token);
        response.setUserId(user.getUserId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().toString());

        return response;
    }

}
