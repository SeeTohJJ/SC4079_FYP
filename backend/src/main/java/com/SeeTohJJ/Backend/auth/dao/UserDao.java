package com.SeeTohJJ.Backend.auth.dao;

import com.SeeTohJJ.Backend.auth.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    boolean findEmailExist(String email);
    void registerUser(User user);
    User findUserByEmail(String email);
}
