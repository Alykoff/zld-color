package com.zld.data;

import java.util.Comparator;
import java.util.Objects;

public class Paint implements Comparator<Paint> {
    final int number;
    final PaintType type;

    public Paint(int number, PaintType type) {
        this.number = number;
        this.type = type;
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
