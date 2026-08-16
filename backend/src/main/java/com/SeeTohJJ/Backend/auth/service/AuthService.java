package com.SeeTohJJ.Backend.auth.service;

import com.SeeTohJJ.Backend.auth.dto.AuthResponseDTO;
import com.SeeTohJJ.Backend.auth.dto.LoginRequestDTO;
import com.SeeTohJJ.Backend.auth.dto.RegisterRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    String register(RegisterRequestDTO request);
    AuthResponseDTO login(LoginRequestDTO request);
    AuthResponseDTO adminLogin(LoginRequestDTO request);

}
