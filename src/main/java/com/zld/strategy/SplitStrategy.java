package com.zld.strategy;

import com.zld.data.*;
import com.zld.strategy.data.StrategyResult;
import com.zld.test.StrategyUtil;

import java.util.*;

public class SplitStrategy implements Strategy {

    public static final int BRUTE_FORCE_BOUND = 23;
    private static final Strategy BRUTE_FORCE = new BruteForceStrategy();

    @Override
    public StrategyResult calc(TestCase testCase) {
        final Group group = testCase.getGroup();
        final int numberOfColors = testCase.getNumberOfColors();
        final int numberOfClients = testCase.getNumberOfClients();
        return calc(group, numberOfColors, numberOfClients);
    }


    public StrategyResult calc(Group group, int numberOfColors, int numberOfClients) {
        final Map<Integer, ClientsPaintSet> nodes = group.getNodes();
//        final Queue<PaintElement> queueProgress = new PriorityQueue<>();

        if (numberOfColors <= BRUTE_FORCE_BOUND) {
            return BRUTE_FORCE.calc(group, numberOfColors, numberOfClients);
        }

        while (true) {
            int minColor = -1;
            int minSize = -1;
            List<Group> minGroup0 = null;
            List<Group> minGroup1 = null;
            for (int color = 0; color < numberOfColors; color++) {
                final ClientsPaintSet clientsPaintSet = nodes.get(color);
                final Set<Client> clients0 = group.getClients();
                final Set<Client> clients1 = group.getClients();
                clients0.removeAll(clientsPaintSet._0().getClients());
                clients1.removeAll(clientsPaintSet._1().getClients());
                final Group groupWithoutCurrentColor0 = Group.removeIndex(new Group(clients0), color);
                final Group groupWithoutCurrentColor1 = Group.removeIndex(new Group(clients1), color);
                final List<Group> groups0 = StrategyUtil.processGroup(groupWithoutCurrentColor0);
                final List<Group> groups1 = StrategyUtil.processGroup(groupWithoutCurrentColor1);
                final int size0 = groups0.stream().map(Group::size).max(Integer::compareTo).orElse(Integer.MAX_VALUE);
                final int size1 = groups1.stream().map(Group::size).max(Integer::compareTo).orElse(Integer.MAX_VALUE);
                final int maxGroupSize = Math.max(size0, size1);
                if (minSize < 0 || minSize > maxGroupSize) {
                    minColor = color;
                    minSize = maxGroupSize;
                    minGroup0 = groups0;
                    minGroup1 = groups1;
                }
            }

            if (minGroup0 == null) {
                throw new RuntimeException();
            }



            return null;
        }
    }

    public static class PaintElement implements Comparable<PaintElement> {
        final Paint paint;
        final List<Paint> prevPoints;
        final List<Integer> removedClients;
        final int score;

        public PaintElement(Paint paint, List<Paint> prevPoints, List<Integer> removedClients, int score) {
            this.paint = paint;
            this.prevPoints = prevPoints;
            this.removedClients = removedClients;
            this.score = score;
        }

        public Paint getPaint() {
            return paint;
        }

        public List<Paint> getPrevPoints() {
            return prevPoints;
        }

        public List<Integer> getRemovedClients() {
            return removedClients;
        }

        public int getScore() {
            return score;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PaintElement that = (PaintElement) o;
            return score == that.score &&
                    Objects.equals(paint, that.paint) &&
                    Objects.equals(prevPoints, that.prevPoints) &&
                    Objects.equals(removedClients, that.removedClients);
        }

        @Override
        public int hashCode() {
            return Objects.hash(paint, prevPoints, removedClients, score);
        }

        @Override
        public String toString() {
            return "PaintElement{" +
                    "paint=" + paint +
                    ", prevPoints=" + prevPoints +
                    ", removedClients=" + removedClients +
                    ", score=" + score +
                    '}';
        }

        @Override
        public int compareTo(PaintElement that) {
            if (that.score > this.score) return 1;
            else if (that.score < this.score) return -1;
            else return 0;
        }
    }
}
