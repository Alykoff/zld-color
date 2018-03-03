package com.zld.strategy.data;

import com.zld.data.Paint;

import java.util.ArrayList;
import java.util.List;

public class SuccessStrategyResult implements StrategyResult {
    private final String result;
    private final List<Paint> paints = new ArrayList<>();

    public SuccessStrategyResult(String result, List<Paint> paints) {
        this.result = result;
        this.paints.addAll(paints);
    }

    @Override
    public String toString() {
        return "SuccessStrategyResult{" +
                "result='" + result + '\'' +
                '}';
    }

    public List<Paint> getPaints() {
        return new ArrayList<>(paints);
    }

    @Override
    public String getResult() {
        return result;
    }
}
