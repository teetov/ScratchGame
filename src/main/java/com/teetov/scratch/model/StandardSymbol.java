package com.teetov.scratch.model;

import com.teetov.scratch.exception.ScratchGameException;

import java.math.BigDecimal;

public class StandardSymbol extends Symbol {

    private final BigDecimal rewardMultiplier;

    public StandardSymbol(String name, BigDecimal rewardMultiplier) {
        super(name);
        if (rewardMultiplier == null) {
            throw new ScratchGameException("Reward multiplier for symbol " + name + " is empty");
        }
        this.rewardMultiplier = rewardMultiplier;
    }

    public BigDecimal getRewardMultiplier() {
        return rewardMultiplier;
    }

}
