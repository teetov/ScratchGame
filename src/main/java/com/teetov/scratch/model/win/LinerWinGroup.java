package com.teetov.scratch.model.win;

import com.teetov.scratch.dto.WinCombination;
import com.teetov.scratch.exception.ScratchGameException;
import com.teetov.scratch.model.GameField;
import com.teetov.scratch.model.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinerWinGroup extends WinCombinationGroup {

    private final WonCombination wonCombination;
    private final List<List<Integer[]>> coveredAreas = new ArrayList<>();

    protected LinerWinGroup(
            String groupName,
            Map<String, WinCombination> winCombinationGroup
    ) {
        super(groupName);
        if (winCombinationGroup.size() > 1) {
            throw new ScratchGameException("Linear symbols win condition should have only one item in every group");
        }
        Map.Entry<String, WinCombination> combinationEntry = new ArrayList<>(winCombinationGroup.entrySet()).get(0);
        WinCombination winCombination = combinationEntry.getValue();
        this.wonCombination = new WonCombination(combinationEntry.getKey(), winCombination.getRewardMultiplier());

        winCombination.getCoveredAreas().forEach(lineConfig -> {
            ArrayList<Integer[]> lineParsed = new ArrayList<>();
            coveredAreas.add(lineParsed);
            lineConfig.forEach(coordinatesConfig ->
                lineParsed.add(parseCoordinate(coordinatesConfig))
            );
        });
    }

    private Integer[] parseCoordinate(String coordinatesConfig) {
        if (!coordinatesConfig.matches("\\d+:\\d+")) {
            throw new ScratchGameException("Unexpected covered area format " + coordinatesConfig);
        }
        String[] splitCoordinates = coordinatesConfig.split(":");
        Integer row = Integer.valueOf(splitCoordinates[0]);
        Integer column = Integer.valueOf(splitCoordinates[1]);
        return new Integer[]{row, column};
    }

    @Override
    public Map<Symbol, WonCombination> getWonCombinations(GameField gameField) {
        List<List<Symbol>> matrix = gameField.getMatrix();
        Map<Symbol, WonCombination> result = new HashMap<>();
        coveredAreas.forEach(area ->
            addToResultIfAreaFilledWithSameSymbol(matrix, result, area)
        );
        return result;
    }

    private void addToResultIfAreaFilledWithSameSymbol(
            List<List<Symbol>> matrix,
            Map<Symbol, WonCombination> result,
            List<Integer[]> area
    ) {
        Symbol first = null;
        for (int i = 0; i < area.size(); i++) {
            Integer[] coordinate = area.get(i);
            Symbol symbol = matrix.get(coordinate[0]).get(coordinate[1]);
            if (result.containsKey(symbol)) {
                break;
            }
            if (first == null) {
                first = symbol;
            } else if (symbol != first) {
                break;
            }
            if (i == area.size() - 1) {
                result.put(symbol, wonCombination);
            }
        }
    }
}
