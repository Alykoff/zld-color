package com.zld.strategy.data;

public class NotFoundStrategyResult implements StrategyResult {
    @Override
    public String getResult() {
        return "IMPOSSIBLE";
    }
}
