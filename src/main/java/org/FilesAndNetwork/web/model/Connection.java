package org.FilesAndNetwork.web.model;

public class Connection {
    private final Station station1;
    private final Station station2;

    public Connection(Station station1, Station station2) {
        this.station1 = station1;
        this.station2 = station2;
    }

    @Override
    public String toString() {
        return station1 + " ↔ " + station2;
    }
}
