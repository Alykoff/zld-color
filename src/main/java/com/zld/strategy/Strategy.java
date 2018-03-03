package com.zld.strategy;

import com.zld.data.Group;
import com.zld.data.TestCase;
import com.zld.strategy.data.StrategyResult;

public interface Strategy {
    StrategyResult calc(TestCase testCase);
    StrategyResult calc(Group group, int numberOfColors, int numberOfClients);
}
