package org.FilesAndNetwork.model;

public class Line {
    String number;
    String name;

    public Line(String number, String name) {
        this.number = number;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Line{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
