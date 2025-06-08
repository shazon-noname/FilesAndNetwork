package org.FilesAndNetwork.web.model;

import java.util.List;

public class Connection {
    private final Station station1;
    private final Station station2;

    public Connection(Station station1, Station station2) {
        this.station1 = station1;
        this.station2 = station2;
    }

    public Station getStation1() {
        return station1;
    }

    public Station getStation2() {
        return station2;
    }

    @Override
    public String toString() {
        return station1 + " ↔ " + station2;
    }

    public List<Station> getStations() {
        return java.util.List.of(station1, station2);
    }
}
