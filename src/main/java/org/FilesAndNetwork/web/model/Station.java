package org.FilesAndNetwork.web.model;

public class Station {
    private final String name;
    private final String lineNumber;

    public Station(String name, String lineNumber) {
        this.name = name;
        this.lineNumber = lineNumber;
    }

    public String getName() {
        return name;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return name + " (линия " + lineNumber + ")";
    }
}
