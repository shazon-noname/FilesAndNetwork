package org.FilesAndNetwork.model;

import org.jsoup.nodes.Element;

public class Station {
    String name;
    String lineNumber;

    public Station(String name, String lineNumber) {
        this.name = name;
        this.lineNumber = lineNumber;
    }
}