package com.zld.strategy;

import com.zld.data.ClientsPaintSet;
import com.zld.data.Group;
import com.zld.data.TestCase;
import com.zld.strategy.data.StrategyResult;

import java.util.Map;

public class AStarStrategy implements Strategy {
    @Override
    public StrategyResult calc(TestCase testCase) {
        return calc(testCase.getGroup(), testCase.getNumberOfColors(), testCase.getNumberOfClients());
    }

    @Override
    public StrategyResult calc(Group group, int numberOfColors, int numberOfClients) {
        for (Map.Entry<Integer, ClientsPaintSet> entry : group.getNodes().entrySet()) {

        }
        return null;
    }
}
