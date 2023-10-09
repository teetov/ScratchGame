package com.teetov.scratch.model;

import com.teetov.scratch.exception.ScratchGameException;

import java.util.HashMap;
import java.util.Map;

public class Symbols {

    private final Map<String, Symbol> symbolMap = new HashMap<>();

    public Symbols(Map<String, com.teetov.scratch.dto.Symbol> symbolsConfig) {
        symbolsConfig.forEach((name, sbl) -> {
            symbolMap.put(name, new Symbol(name));
        });
    }

    public Symbol get(String displayedName) {
        Symbol symbol = symbolMap.get(displayedName);
        if (symbol == null) {
            throw new ScratchGameException("Symbol not specified " + displayedName);
        }
        return symbol;
    }
}
