package org.FilesAndNetwork.web;

import org.FilesAndNetwork.web.model.Connection;
import org.FilesAndNetwork.web.model.Line;
import org.FilesAndNetwork.web.model.Station;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MetroParser {
    private final List<Line> lines;
    private final List<Station> stations;
    private final Set<Connection> connections;

    public MetroParser() {
        this.lines = new ArrayList<>();
        this.stations = new ArrayList<>();
        this.connections = new HashSet<>(); // Для автоматического устранения дубликатов
    }

    public void parse() throws IOException {
        try {
            Document doc = Jsoup.connect("https://skillbox-java.github.io/")
                    .timeout(10000) // Увеличенный таймаут
                    .get();
            parseLines(doc);
            parseStations(doc);
            parseConnections(doc);
        } catch (IOException e) {
            throw new IOException("Error parsing metro: " + e.getMessage());
        }
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
            if (lineNumber.isEmpty()) continue;
            Elements stationList = element.select("p.single-station");
            for (Element station : stationList) {
                String name = station.select("span.name").text();
                if (!name.isEmpty()) stations.add(new Station(name, lineNumber));
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
                    String title = icon.attr("title");

                    Pattern pattern = Pattern.compile("«([^»]+)»");
                    Matcher matcher = pattern.matcher(title);
                    if (!matcher.find()) continue;
                    String transferStationName = matcher.group(1).trim();
                    Station currentStation = stationMap.get(stationName + "_" + lineNumber);
                    Station transferStation = stationMap.get(transferStationName + "_" + transferLine);
                    if (currentStation != null && transferStation != null) {
                        Connection connection = new Connection (currentStation, transferStation);
                        connections.add(connection);
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
        return new ArrayList<>(connections); // Конвертируем Set в List
    }
}