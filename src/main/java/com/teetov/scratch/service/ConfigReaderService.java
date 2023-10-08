package com.teetov.scratch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teetov.scratch.exception.ScratchGameException;
import com.teetov.scratch.dto.GameConfig;

import java.io.File;
import java.io.IOException;

public class ConfigReaderService {
    private final ObjectMapper mapper;

    public ConfigReaderService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public GameConfig readConfig(String pathToFile) {
        File file = new File(pathToFile);
        try {
            return mapper.readValue(file, GameConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ScratchGameException("Reading config file failed");
        }
    }
}
