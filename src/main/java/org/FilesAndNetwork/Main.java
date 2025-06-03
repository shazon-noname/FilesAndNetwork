package org.FilesAndNetwork;

import org.FilesAndNetwork.model.Line;
import org.FilesAndNetwork.model.Station;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        MetroParser metroParser = new MetroParser();
        metroParser.parse();
        List<Line> lines = metroParser.getLines();
        List<Station> stations = metroParser.getStations();

        lines.forEach(System.out::println);
        stations.forEach(System.out::println);
    }
}
