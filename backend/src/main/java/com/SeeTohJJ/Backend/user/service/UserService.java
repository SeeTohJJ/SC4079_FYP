package com.SeeTohJJ.Backend.user.service;

import com.SeeTohJJ.Backend.auth.dto.RegisterRequestDTO;
import com.SeeTohJJ.Backend.auth.model.User;
import com.SeeTohJJ.Backend.user.model.UserProfile;

import java.util.List;

public interface UserService {

    void insertAllUserInitialTables(Long userId, RegisterRequestDTO request);
}
