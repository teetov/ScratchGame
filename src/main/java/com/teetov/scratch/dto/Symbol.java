package com.teetov.scratch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Symbol {
    @JsonProperty("reward_multiplier")
    private BigDecimal rewardMultiplier;
    private String type;
    private String impact;
    private Integer extra;

    public BigDecimal getRewardMultiplier() {
        return rewardMultiplier;
    }

    public void setRewardMultiplier(BigDecimal rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public Integer getExtra() {
        return extra;
    }

    public void setExtra(Integer extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "rewardMultiplier=" + rewardMultiplier +
                ", type='" + type + '\'' +
                ", impact='" + impact + '\'' +
                ", extra=" + extra +
                '}';
    }
}
