package org.FilesAndNetwork;

import org.FilesAndNetwork.util.FileFinder;
import org.FilesAndNetwork.web.model.*;
import org.FilesAndNetwork.web.parser.CSVParser;
import org.FilesAndNetwork.web.parser.JsonParser;
import org.FilesAndNetwork.web.parser.MetroParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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

        System.out.println("------Files in 'data' directory with extensions csv and json------");
        FileFinder fileFinder = new FileFinder();
        List<File> files = fileFinder.findFiles("data", "csv", "json");
        files.forEach(System.out::println);

        System.out.println("------Parsing JSON file for station depths------");
        JsonParser jp = new JsonParser();
        List<StationDepth> allDepths = new ArrayList<>();
        for (File file : files) {
            if (file.getName().endsWith(".json")) {
                List<StationDepth> parse = jp.parse(file);
                allDepths.addAll(parse);
                System.out.println("Parsed depths from file: " + file.getName());
            }
        }
        IntStream.range(0,allDepths.size())
                .forEach(i -> System.out.println((i+1) + ". " + allDepths.get(i)));


        System.out.println("------Parsing CSV file for station depths------");
        List<StationInfo> allDepthsFromCSV = new ArrayList<>();
        CSVParser csvParser = new CSVParser();
        for (File file : files) {
            if (file.getName().endsWith(".csv")) {
                List<StationInfo> parse = csvParser.parse(String.valueOf(file));
                allDepthsFromCSV.addAll(parse);
                System.out.println("Parsed depths from file: " + file.getName());
            }
        }
        IntStream.range(0, allDepthsFromCSV.size())
                .forEach(i -> System.out.println((i+1) + ". " + allDepthsFromCSV.get(i)));

    }
}
