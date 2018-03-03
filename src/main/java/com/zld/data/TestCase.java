package com.zld.data;

public class TestCase {
    private final int numberOfColors;
    private final int numberOfClients;
    private final Group group;
    private final int number;

    public TestCase(int numberOfColors, int numberOfClients, Group group, int number) {
        this.numberOfColors = numberOfColors;
        this.numberOfClients = numberOfClients;
        this.group = group;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int getNumberOfColors() {
        return numberOfColors;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public Group getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return "TestCase{" +
                "numberOfColors=" + numberOfColors +
                ", numberOfClients=" + numberOfClients +
                ", group=" + group +
                ", number=" + number +
                '}';
    }
}
