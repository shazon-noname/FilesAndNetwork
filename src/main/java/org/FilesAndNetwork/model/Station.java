package org.FilesAndNetwork.model;

public class Station {
    String lineNumber;
    String name;

    public Station(String lineNumber,String name ) {
        this.lineNumber = lineNumber;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Station{" +
                "lineNumber='" + lineNumber + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}