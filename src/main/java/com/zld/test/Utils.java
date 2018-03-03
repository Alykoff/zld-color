package com.zld.test;

import com.zld.data.Client;
import com.zld.data.Group;
import com.zld.data.TestCase;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Utils {

    public static List<TestCase> loadDataFromFile(String path) throws IOException {
        final List<String> lines = getLinesFromFile(path);
        return loadData(lines.iterator());
    }

    public static List<String> getLinesFromFile(String fileName) throws IOException {
        return Files.readAllLines(
                Paths.get(fileName), Charset.forName("UTF-8")
        );
    }

    public static String getPathForFileFromResource(String fileName) throws NullPointerException {
        return Utils.class.getClassLoader().getResource(fileName).getFile();
    }

    public static List<TestCase> loadDataFromInput() {
        final Scanner in = new Scanner(System.in);
        return loadData(in);
    }

    private static List<TestCase> loadData(Iterator<String> lineIterator) {
        final List<TestCase> result = new ArrayList<>();
        final int numberOfTests = Integer.parseInt(lineIterator.next().trim());
        int countOfTests = 0;
        while (countOfTests++ < numberOfTests) {
            final int numberOfColors = Integer.parseInt(lineIterator.next().trim());
            final int numberOfClients = Integer.parseInt(lineIterator.next().trim());
            int clientCounter = 0;
            final List<Client> clients = new ArrayList<>();
            while (clientCounter++ < numberOfClients) {
                clients.add(
                        new Client(lineIterator.next().trim(), clientCounter)
                );
            }
            final Group group = new Group(clients);
            result.add(new TestCase(numberOfColors, numberOfClients, group, countOfTests));
        }
        return result;
    }

    public static void printListGroup(List<Group> testCases) {
        final StringBuilder builder = new StringBuilder();
        for (Group group : testCases) {
            group.getNodes().forEach((key, value) -> {
                builder.append("=======\n")
                        .append(group)
                        .append("\n");
            });
        }
        System.out.println(builder.toString());
    }
}
