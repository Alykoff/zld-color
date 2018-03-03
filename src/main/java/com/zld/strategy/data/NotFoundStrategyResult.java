package com.zld.strategy.data;

import com.zld.strategy.data.StrategyResult;

public class NotFoundStrategyResult implements StrategyResult {
    @Override
    public String getResult() {
        return "IMPOSSIBLE";
    }
}
