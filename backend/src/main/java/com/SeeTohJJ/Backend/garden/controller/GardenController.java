package com.SeeTohJJ.Backend.garden.controller;

import com.SeeTohJJ.Backend.auth.service.JwtService;
import com.SeeTohJJ.Backend.garden.dto.GardenDTO;
import com.SeeTohJJ.Backend.garden.dto.PlantDTO;
import com.SeeTohJJ.Backend.garden.dto.request.WaterPlantRequest;
import com.SeeTohJJ.Backend.garden.service.GardenService;
import com.SeeTohJJ.Backend.study.dto.NodeRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/garden")
public class GardenController {

    private static final Logger logger = LoggerFactory.getLogger(GardenController.class);
    private final JwtService jwtService;
    private final GardenService gardenService;

    @Autowired
    public GardenController(JwtService jwtService,
                            GardenService gardenService) {
        this.jwtService = jwtService;
        this.gardenService = gardenService;
    }


    @PostMapping("/getGarden")
    public ResponseEntity<GardenDTO> getGardenInfo(@RequestHeader("Authorization") String authHeader) {
        logger.info("Starting getGardenInfo");

        Long userId = jwtService.extractUserId(authHeader.substring(7));
        GardenDTO garden = gardenService.getGarden(userId);

        return ResponseEntity.ok(garden);
    }

    @PostMapping("/waterPlant")
    public ResponseEntity<PlantDTO> waterPlant(@RequestHeader("Authorization") String authHeader,
                                               @RequestBody WaterPlantRequest request) {
        logger.info("Starting waterPlant");

        Long userId = jwtService.extractUserId(authHeader.substring(7));

        return ResponseEntity.ok(gardenService.waterPlant(userId, request.getTopicId()));
    }
}
