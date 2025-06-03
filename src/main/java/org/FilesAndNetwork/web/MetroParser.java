package org.FilesAndNetwork.web;

import org.FilesAndNetwork.web.model.Connection;
import org.FilesAndNetwork.web.model.Line;
import org.FilesAndNetwork.web.model.Station;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetroParser {
    private final List<Line> lines;
    private final List<Station> stations;
    private final List<Connection> connections;

    public MetroParser() {
        this.lines = new ArrayList<>();
        this.stations = new ArrayList<>();
        this.connections = new ArrayList<>();
    }

    public void parse() throws IOException {
        Document doc = Jsoup.connect("https://skillbox-java.github.io/").get();
        parseLines(doc);
        parseStations(doc);
        parseConnections(doc);
    }

    private void parseLines(Document doc) {
        Elements lineElements = doc.select("span.js-metro-line");
        for (Element element : lineElements) {
            String number = element.attr("data-line");
            String name = element.text().replace("≈", "").trim();
            lines.add(new Line(number, name));
        }
    }

    private void parseStations(Document doc) {
        Elements stationElements = doc.select("div.js-metro-stations[data-line]");
        for (Element element : stationElements) {
            String lineNumber = element.attr("data-line");
            Elements stationList = element.select("p.single-station");
            for (Element station : stationList) {
                String name = station.select("span.name").text();
                stations.add(new Station(name, lineNumber));
            }
        }
    }

    private void parseConnections(Document doc) {
        Map<String, Station> stationMap = new HashMap<>();
        for (Station station : stations) {
            stationMap.put(station.getName() + "_" + station.getLineNumber(), station);
        }
        Elements stationContainers = doc.select("div.js-metro-stations[data-line]");
        for (Element container : stationContainers) {
            String lineNumber = container.attr("data-line");
            Elements stationsWithTransfers = container.select("p.single-station");
            for (Element stationElement : stationsWithTransfers) {
                String stationName = stationElement.select("span.name").text();
                Elements transferIcons = stationElement.select("span.t-icon-metroln");
                for (Element icon : transferIcons) {
                    String transferLine = icon.className().split(" ")[1].replace("ln-", "");
                    String transferStationName = icon.attr("title").replace("переход на станцию «", "").replace("»", "").split(" ")[0];
                    Station transferStation = stationMap.get(transferStationName + "_" + transferLine);
                    if (transferStation != null) {
                        Station currentStation = stationMap.get(stationName + "_" + lineNumber);
                        connections.add(new Connection(currentStation, transferStation));
                    }
                }
            }
        }
    }

    public List<Line> getLines() {
        return lines;
    }

    public List<Station> getStations() {
        return stations;
    }

    public List<Connection> getConnections() {
        return connections;
    }
}