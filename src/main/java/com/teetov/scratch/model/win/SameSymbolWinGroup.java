package com.teetov.scratch.model.win;

import com.teetov.scratch.dto.WinCombination;
import com.teetov.scratch.exception.ScratchGameException;
import com.teetov.scratch.model.GameField;
import com.teetov.scratch.model.StandardSymbol;
import com.teetov.scratch.model.Symbol;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SameSymbolWinGroup extends WinCombinationGroup {

    private final SortedMap<Integer, WonCombination> symbolCountRewardMap;

    protected SameSymbolWinGroup(
            String groupName,
            Map<String, WinCombination> winCombinationGroup
    ) {
        super(groupName);
        this.symbolCountRewardMap = new TreeMap<>();
        winCombinationGroup.forEach((name, combination) -> {
            if (combination.getCount() == null) {
                throw new ScratchGameException("Win combination count for " + name + " is empty");
            }
            symbolCountRewardMap.put(combination.getCount(), new WonCombination(name, combination.getRewardMultiplier()));
        });
    }

    @Override
    public Map<Symbol, WonCombination> getWonCombinations(GameField gameField) {
        Map<Symbol, Long> symbolsCount = countAllStandardSymbols(gameField);
        Integer minRewardCount = symbolCountRewardMap.firstKey();

        return symbolsCount.entrySet().stream()
                .filter(entry -> entry.getValue() >= minRewardCount)
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> getWonCombination(entry.getValue())));
    }

    private WonCombination getWonCombination(Long symbolCount) {
        int symbolCountInt = symbolCount.intValue();
        Integer bestCombination = symbolCountRewardMap.subMap(0, symbolCountInt + 1).lastKey();
        return symbolCountRewardMap.get(bestCombination);
    }

    private Map<Symbol, Long> countAllStandardSymbols(GameField gameField) {
        List<List<Symbol>> matrix = gameField.getMatrix();
        return matrix.stream()
                .flatMap(Collection::stream)
                .filter(StandardSymbol.class::isInstance)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}
