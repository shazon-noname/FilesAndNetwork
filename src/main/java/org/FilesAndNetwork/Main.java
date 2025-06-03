package org.FilesAndNetwork;

import org.FilesAndNetwork.file.FileFinder;
import org.FilesAndNetwork.web.model.Connection;
import org.FilesAndNetwork.web.model.Line;
import org.FilesAndNetwork.web.model.Station;
import org.FilesAndNetwork.web.MetroParser;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        MetroParser metroParser = new MetroParser();
        metroParser.parse();
        List<Line> lines = metroParser.getLines();
        List<Station> stations = metroParser.getStations();
        List<Connection> connections = metroParser.getConnections();

        System.out.println("------Connections------");
        connections.forEach(System.out::println);
        System.out.println("-------Lines-----");
        lines.forEach(System.out::println);
        System.out.println("---------Stations--------");
        stations.forEach(System.out::println);


        System.out.println();

        FileFinder fileFinder = new FileFinder();
        List<File> files = fileFinder.findFiles("data", "csv");
        files.forEach(System.out::println);
    }
}
