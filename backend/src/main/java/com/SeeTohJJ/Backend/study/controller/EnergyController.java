package com.SeeTohJJ.Backend.study.controller;

import com.SeeTohJJ.Backend.auth.service.JwtService;
import com.SeeTohJJ.Backend.study.dto.gameplay.EnergyResponseDTO;
import com.SeeTohJJ.Backend.study.service.gameplay.EnergyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/energy")
public class EnergyController {
    private static final Logger logger = LoggerFactory.getLogger(EnergyController.class);

    private final EnergyService energyService;
    private final JwtService jwtService;

    public EnergyController(EnergyService energyService, JwtService jwtService) {
        this.energyService = energyService;
        this.jwtService = jwtService;
    }

    @GetMapping("/get_energy")
    public EnergyResponseDTO getEnergy(@RequestHeader("Authorization") String authHeader) {
        logger.info("Starting getEnergy");

        Long userId = jwtService.extractUserId(authHeader.substring(7));
        return energyService.getEnergy(userId);
    }
}
