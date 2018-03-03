package com.zld.data;

public enum PaintType {
    _0(0),
    _1(1);
    int id;
    PaintType(int id) {
        this.id = id;
    }

    int getId() {
        return id;
    }

    public static PaintType fromId(int id) {
        if (id == 0) {
            return _0;
        } else if (id == 1) {
            return _1;
        } else {
            throw new RuntimeException("Unknown id " + id);
        }
    }
}
