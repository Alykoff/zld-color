package com.zld.data;

import com.zld.test.StrategyUtil;

import java.util.*;
import java.util.stream.Collectors;

// TODO number of colors
public class Group {
    private Map<Integer, ClientsPaintSet> nodes = new TreeMap<>();
    private Set<Client> clients;

    public Group(Group otherGroup) {
        this(
            otherGroup.nodes
                .values()
                .stream()
                .flatMap(x -> x.getClients().stream())
                .collect(Collectors.toSet())
        );
    }

    public Group(Collection<Client> clients) {
        buildGroup(clients, nodes);
        this.clients = Collections.unmodifiableSet(new HashSet<>(clients));
    }

    private static void buildGroup(Collection<Client> clients, Map<Integer, ClientsPaintSet> nodes) {
        clients.forEach(client ->
            client.favorite.forEach(paint ->
                nodes.compute(paint.number, (key, val) -> {
                    if (val == null) {
                        val = new ClientsPaintSet();
                    }
                    if (paint.type == PaintType._0) {
                        val._0.clients.add(client);
                    } else if (paint.type == PaintType._1) {
                        val._1.clients.add(client);
                    } else {
                        throw new RuntimeException();
                    }
                    return val;
                })
            )
        );
    }

    public Set<Client> getClients() {
        return new HashSet<>(clients);
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        nodes.forEach((key, value) -> {
            builder.append(key).append("\t\t| ")
                    .append(value._0.clients.stream().map(c -> c.clientId).collect(Collectors.toList()))
                    .append(" <<>> ")
                    .append(value._1.clients.stream().map(c -> c.clientId).collect(Collectors.toList()))
                    .append('\n');
        });
        return builder.toString();
    }

    public int size() {
        return nodes.size();
    }

    public Map<Integer, ClientsPaintSet> getNodes() {
        return new HashMap<>(nodes);
    }

    public static Group removeClients(Group group, Collection<Client> removeClients) {
        final Set<Client> clients = group.getClients();
        clients.removeAll(removeClients);
        return new Group(clients);
    }

    public static Group removeIndex(Group group, int index) {
        final Map<Integer, ClientsPaintSet> result = group.getNodes();
        result.remove(index);
        return new Group(StrategyUtil.clientsFromMap(result));
    }

    public static Group removeIndex(Group group, Set<Integer> indexes) {
        final Map<Integer, ClientsPaintSet> result = group.getNodes();
        for (int i : indexes) {
            result.remove(i);
        }
        return new Group(StrategyUtil.clientsFromMap(result));
    }
}
