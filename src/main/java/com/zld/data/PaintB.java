package com.zld.data;

import java.util.*;

public class PaintB implements Comparator<Paint> {
    final int number;
    final PaintType type;
    final Set<Client> clients = new HashSet<>();

    public PaintB(int number, PaintType type, List<Client> clients) {
        this.number = number;
        this.type = type;
        this.clients.addAll(clients);
    }

    public Set<Client> getClients() {
        return new HashSet<>(clients);
    }

    public int getNumber() {
        return number;
    }

    public PaintType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "P[" + number +
                ", " + type +
                ']';
    }

    @Override
    public int compare(Paint p1, Paint p2) {
        if (p1.number < p2.number) return 1;
        else if (p1.number > p2.number) return -1;
        else if (p1.type == PaintType._0 && p2.type == PaintType._1) return 1;
        else if (p1.type == PaintType._1 && p2.type == PaintType._0) return -1;
        else return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paint paint = (Paint) o;
        return number == paint.number &&
                type == paint.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, type);
    }
}
