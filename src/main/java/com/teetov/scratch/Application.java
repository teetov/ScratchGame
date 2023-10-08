package com.teetov.scratch;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.teetov.scratch.dto.GameConfig;
import com.teetov.scratch.model.GameParameters;
import com.teetov.scratch.service.ConfigReaderService;

public class Application {


    public static void main(String[] args) {
        GameParameters gameParameters = new GameParameters(args);

        ObjectMapper mapper = new ObjectMapper();
        ConfigReaderService readerService = new ConfigReaderService(mapper);
        GameConfig gameConfig = readerService.readConfig(gameParameters.get(GameParameters.Property.CONFIG));

    }

}
