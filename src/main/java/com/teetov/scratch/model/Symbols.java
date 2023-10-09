package com.teetov.scratch.model;

import com.teetov.scratch.exception.ScratchGameException;

import java.util.HashMap;
import java.util.Map;

public class Symbols {

    private final Map<String, Symbol> symbolMap = new HashMap<>();

    public Symbols(Map<String, com.teetov.scratch.dto.Symbol> symbolsConfig) {
        symbolsConfig.forEach((name, sbl) -> {
            if (sbl.getType().equals("standard")) {
                symbolMap.put(name, new StandardSymbol(name, sbl.getRewardMultiplier()));
            } else if (sbl.getType().equals("bonus")) {
                symbolMap.put(name, new BonusSymbol(name, sbl.getImpact(), sbl.getRewardMultiplier(), sbl.getExtra()));
            } else {
                throw new ScratchGameException("Unknown symbol type: " + sbl.getType());
            }
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
