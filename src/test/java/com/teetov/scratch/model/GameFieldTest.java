package com.teetov.scratch.model;

import com.teetov.scratch.dto.BonusSymbols;
import com.teetov.scratch.dto.Probabilities;
import com.teetov.scratch.dto.StandardSymbols;
import com.teetov.scratch.dto.Symbol;
import com.teetov.scratch.exception.ScratchGameException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameFieldTest {

    @Test
    void generateGameField() {
        int columns = 3;
        int rows = 3;

        Probabilities probabilities = generatePossibilities(columns, rows);
        BonusSymbols bonusSymbols = generateBonusSymbols(probabilities);
        probabilities.setBonusSymbols(bonusSymbols);
        Symbols symbols = generateSymbols();

        GameField gameField = new GameField(columns, rows, probabilities, symbols);

        List<List<com.teetov.scratch.model.Symbol>> matrix = gameField.getMatrix();

        assertEquals(rows, matrix.size());
        for (int i = 0; i < rows; i++) {
            assertEquals(columns, matrix.get(i).size());
        }

        assertNotNull(gameField.getBonus());
        assertTrue(bonusSymbols.getSymbols().containsKey(gameField.getBonus().getName()));
    }

    @Test
    void notConsistentConfig() {
        int columns = 3;
        int rows = 3;

        Probabilities probabilities = generatePossibilities(columns, rows);
        BonusSymbols bonusSymbols = generateBonusSymbols(probabilities);
        probabilities.setBonusSymbols(bonusSymbols);
        Symbols symbols = generateSymbols();

        assertThrows(ScratchGameException.class, () -> new GameField(1, 2, probabilities, symbols));
    }

    private Probabilities generatePossibilities(int columns, int rows) {
        Probabilities probabilities = new Probabilities();
        ArrayList<StandardSymbols> standardSymbols = generateStandardSymbols(columns, rows);
        probabilities.setStandardSymbols(standardSymbols);
        return probabilities;
    }

    private ArrayList<StandardSymbols> generateStandardSymbols(int columns, int rows) {
        ArrayList<StandardSymbols> standardSymbols = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                StandardSymbols standard = new StandardSymbols();
                standard.setRow(row);
                standard.setColumn(column);
                HashMap<String, Integer> symbols = new HashMap<>();
                symbols.put("A", 1);
                symbols.put("B", 2);
                symbols.put("C", 3);
                symbols.put("D", 4);
                symbols.put("E", 5);
                symbols.put("F", 6);
                standard.setSymbols(symbols);
                standardSymbols.add(standard);
            }
        }
        return standardSymbols;
    }

    private BonusSymbols generateBonusSymbols(Probabilities probabilities) {
        BonusSymbols bonusSymbols = new BonusSymbols();
        HashMap<String, Integer> bonusSymbolsMap = new HashMap<>();
        bonusSymbolsMap.put("10x", 1);
        bonusSymbolsMap.put("5x", 2);
        bonusSymbolsMap.put("+1000", 3);
        bonusSymbolsMap.put("+500", 4);
        bonusSymbolsMap.put("MISS", 5);
        bonusSymbols.setSymbols(bonusSymbolsMap);
        return bonusSymbols;
    }

    private Symbols generateSymbols() {
        HashMap<String, Symbol> symbolsConfig = new HashMap<>();
        symbolsConfig.put("A", new Symbol(BigDecimal.valueOf(50), "standard", null, null));
        symbolsConfig.put("B", new Symbol(BigDecimal.valueOf(25), "standard", null, null));
        symbolsConfig.put("C", new Symbol(BigDecimal.valueOf(10), "standard", null, null));
        symbolsConfig.put("D", new Symbol(BigDecimal.valueOf(5), "standard", null, null));
        symbolsConfig.put("E", new Symbol(BigDecimal.valueOf(3), "standard", null, null));
        symbolsConfig.put("F", new Symbol(BigDecimal.valueOf(1.5), "standard", null, null));
        symbolsConfig.put("10x", new Symbol(BigDecimal.valueOf(10), "bonus", "multiply_reward", null));
        symbolsConfig.put("5x", new Symbol(BigDecimal.valueOf(5), "bonus", "multiply_reward", null));
        symbolsConfig.put("+1000", new Symbol(null, "bonus", "extra_bonus", 1000));
        symbolsConfig.put("+500", new Symbol(null, "bonus", "extra_bonus", 500));
        symbolsConfig.put("MISS", new Symbol(null, "bonus", "miss", null));
        return new Symbols(symbolsConfig);
    }
}
