package com.SeeTohJJ.Backend.user.service;

import com.SeeTohJJ.Backend.user.dto.ProgressResponseDTO;

public interface ProgressService {
    ProgressResponseDTO getProgress(Long userId);
}
