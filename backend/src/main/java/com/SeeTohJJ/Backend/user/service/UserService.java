package com.SeeTohJJ.Backend.user.service;

import com.SeeTohJJ.Backend.auth.dto.RegisterRequestDTO;
import com.SeeTohJJ.Backend.auth.model.User;
import com.SeeTohJJ.Backend.user.model.UserProfile;

public interface UserService {

    void setUserProfile(Long userId, RegisterRequestDTO request);
}
