package com.SeeTohJJ.Backend.user.service;

import com.SeeTohJJ.Backend.auth.dto.request.RegisterRequestDTO;

public interface UserService {

    void insertAllUserInitialTables(Long userId, RegisterRequestDTO request);

    int getActiveUserCount();
    int getActiveAdminCount();
    String getNameFromId(Long userId);
}
