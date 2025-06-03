package org.FilesAndNetwork.web.model;

public class Line {
    private final String number;
    private final String name;

    public Line(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return number + " - " + name;
    }
}
