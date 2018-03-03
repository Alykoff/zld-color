package com.zld.test;

import com.zld.data.*;

import java.util.*;
import java.util.stream.Collectors;

public class StrategyUtil {

    static Group filterGroup(Group group) {
        // remove all clients and paints that not need
        final Set<Client> removeClients = new HashSet<>();
        final Set<Integer> keyForRemove = new HashSet<>();
        while (true) {
            group.getNodes().entrySet().forEach(entry -> {
                final Integer paintNumber = entry.getKey();
                final ClientsPaintSet clientsPaintSet = entry.getValue();
                if (clientsPaintSet._1().getClients().isEmpty() && clientsPaintSet._0().getClients().isEmpty()) {
                    keyForRemove.add(paintNumber);
                } else if (clientsPaintSet._1().getClients().isEmpty()) {
                    removeClients.addAll(clientsPaintSet._0().getClients());
                }
            });
            if (removeClients.isEmpty() && keyForRemove.isEmpty()) {
                break;
            }
            group = Group.removeIndex(group, keyForRemove);
            group = Group.removeClients(group, removeClients);
            keyForRemove.clear();
            removeClients.clear();
        }
        return group;
    }

    private static List<Group> splitGroup(Group group, List<Client> ignoredClients) {
        final List<Group> result = new ArrayList<>();
        final Set<Integer> usedColors = new HashSet<>();
        if (group.getNodes().isEmpty()) {
            return result;
        }
        for (Integer color : group.getNodes().keySet()) {
            if (usedColors.contains(color)) {
                continue;
            }
            final Set<Client> clients = group.getNodes()
                    .get(color)
                    .getClients()
                    .stream()
                    .filter(cl -> !ignoredClients.contains(cl))
                    .collect(Collectors.toSet());
            while (true) {
                final Set<Integer> newColors = clients.stream()
                        .filter(cl -> !ignoredClients.contains(cl))
                        .flatMap(c -> c.getFavorite().stream())
                        .map(Paint::getNumber)
                        .collect(Collectors.toSet());
                final Set<Client> newClients = newColors.stream()
                        .flatMap(clr ->
                                group.getNodes()
                                        .get(clr)
                                        .getClients()
                                        .stream()
                                        .filter(cl -> !ignoredClients.contains(cl))
                        ).collect(Collectors.toSet());
                if (clients.size() == newClients.size()) {
                    result.add(new Group(clients));
                    break;
                }
                clients.addAll(newClients);
            }
            usedColors.addAll(
                    clients.stream().flatMap(c ->
                            c.getFavorite().stream().map(Paint::getNumber)
                    ).collect(Collectors.toSet())
            );
        }
        return result;
    }

    private static List<Group> splitGroup(Group group) {
        return splitGroup(group, Collections.emptyList());
    }

    public static List<Group> processGroup(Group group) {
        filterGroup(group);
        return splitGroup(group);
    }

    public static List<Client> filterTrivialCases(List<Client> clients) {
        clients = new ArrayList<>(clients);
        // get all colors with matte type in client favorite list
        final List<Integer> matteNumbers = clients.stream()
                .flatMap(x -> x.getFavorite().stream())
                .filter(x -> x.getType() == PaintType._1)
                .map(Paint::getNumber)
                .collect(Collectors.toList());
        // get all clients that prefer conflict color.
        final List<Client> nonConflictClients = clients.stream()
                .filter(client ->
                        client.getFavorite()
                                .stream()
                                .anyMatch(paint ->
                                        !matteNumbers.contains(paint.getNumber())
                                )
                ).collect(Collectors.toList());
        clients.removeAll(nonConflictClients);
        return clients;
    }

    public static Collection<Client> clientsFromMap(Map<Integer, ClientsPaintSet> map) {
        return map.values().stream().flatMap(x -> x.getClients().stream()).collect(Collectors.toSet());
    }
}
