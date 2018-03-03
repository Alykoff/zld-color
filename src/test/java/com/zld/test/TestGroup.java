package com.zld.test;

import com.zld.data.TestCase;
import com.zld.strategy.BruteForceStrategy;
import com.zld.strategy.Strategy;
import com.zld.strategy.data.StrategyResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class TestGroup {
    private TestCase testCase;
    private String expect;

    public TestGroup(TestCase testCase, String expect) {
        this.testCase = testCase;
        this.expect = expect;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws IOException, NullPointerException {
        final int maxTest = 2;
        final List<TestCase> testCasesRaw = new ArrayList<>();
        final List<String> answers = new ArrayList<>();
        for (int i = 1; i <= maxTest; i++) {
             testCasesRaw.addAll(Utils.loadDataFromFile(
                    Utils.getPathForFileFromResource("test" + i + ".txt")
            ));
             answers.addAll(Utils.getLinesFromFile(
                    Utils.getPathForFileFromResource("result" + i + ".txt")
            ));
        }
        final List<Object[]> result = new ArrayList<>();
        for (int i = 0; i < testCasesRaw.size(); i++) {
            result.add(new Object[] {testCasesRaw.get(i), answers.get(i)});
        }
        return result;
    }

    @Test
    public void checkSplitGroup1() {
        final Strategy strategy = new BruteForceStrategy();
        final StrategyResult result = strategy.calc(testCase);
        Assert.assertEquals("Case " + testCase.getNumber(), expect, result.getResult());
    }
}
