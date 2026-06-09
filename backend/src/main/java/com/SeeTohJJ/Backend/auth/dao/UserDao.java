package com.SeeTohJJ.Backend.auth.dao;

import com.SeeTohJJ.Backend.auth.model.User;

public interface UserDao {

    boolean findEmailExist(String email);
    Long registerUser(User user);
    User findUserByEmail(String email);
}
