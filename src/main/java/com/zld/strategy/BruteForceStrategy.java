package com.zld.strategy;

import com.zld.data.*;
import com.zld.strategy.data.NotFoundStrategyResult;
import com.zld.strategy.data.StrategyResult;
import com.zld.strategy.data.SuccessStrategyResult;

import java.util.*;

public class BruteForceStrategy implements Strategy {
    public static final int BRUTE_FORCE_NUM_ELEMENTS_BOUND = 23;
    public static final int NOT_EXIST_MIN_BEST_SCORE = -1;

    @Override
    public StrategyResult calc(TestCase testCase) {
        Group group = testCase.getGroup();
        int numberOfColors = testCase.getNumberOfColors();
        int numberOfClients = testCase.getNumberOfClients();
        return calc(group, numberOfColors, numberOfClients);
    }

    public StrategyResult calc(Group group, int numberOfColors, int numberOfClients) {
        if (numberOfColors > BRUTE_FORCE_NUM_ELEMENTS_BOUND) {
            throw new RuntimeException("NumberOfPaints is very big");
        }
        int seq = 0;
        final int maxSeq = calcMaxSeq(numberOfColors);
        String bestResult = null;
        long bestScore = NOT_EXIST_MIN_BEST_SCORE;
        while (seq <= maxSeq) {
            final Set<Client> clients = new HashSet<>();
            // we walk from colors and grep all clients
            for (int i = 0; i < numberOfColors; i++) {
                final ClientsPaintSet paintSet = group.getNodes().get(i);
                if (paintSet != null) {
                    clients.addAll(PaintType.fromId(getBit(seq, i)) == PaintType._0
                            ? paintSet._0().getClients()
                            : paintSet._1().getClients()
                    );
                }
            }
            if (clients.size() == numberOfClients) {
                final String candidate = intToString(seq);
                final long score = candidate.chars().filter(x -> x == '1').count();
                if (score < bestScore || bestScore == NOT_EXIST_MIN_BEST_SCORE) {
                    bestScore = score;
                    bestResult = candidate;
                }
                // Hack case
                if (bestScore >= 0 && bestScore <= 1) {
                    break;
                }
            }
            seq++;
        }
        if (bestResult == null) {
            return new NotFoundStrategyResult();
        }
        return new SuccessStrategyResult(bestResult.substring(0, numberOfColors), Collections.emptyList());
    }

    private static String intToString(int number) {
        final StringBuilder result = new StringBuilder();
        for(int i = 31; i >= 0 ; i--) {
            int mask = 1 << i;
            result.append((number & mask) != 0 ? "1" : "0");

        }
        return result.reverse().toString();
    }

    private static int getBit(int n, int position) {
        return (n >> position) & 1;
    }

    private static boolean changePaint(Paint[] paints) {
        int i = 0;
        while (true) {
            if (paints[i].getType() == PaintType._0) {
                paints[i] = new Paint(paints[i].getNumber(), PaintType._1);
                return false;
            }

        }
    }

    private static int calcMaxSeq(int n) {
        int acc = 0;
        while (n-- > 0) {
            acc += Math.pow(2, n);
        }
        return acc;
    }

    private boolean check(Group testCase, Paint[] paints) {
        return false;
    }
}
