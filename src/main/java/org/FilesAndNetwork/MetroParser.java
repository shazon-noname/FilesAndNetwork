package org.FilesAndNetwork;

import org.FilesAndNetwork.model.Line;
import org.FilesAndNetwork.model.Station;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MetroParser {
    private final List<Line> lines;
    private final List<Station> stations;

    public MetroParser() {
        this.lines = new ArrayList<>();
        this.stations = new ArrayList<>();
    }

    public void parse() throws IOException {
        Document elements = Jsoup.connect("https://skillbox-java.github.io/").get();
        parseLines(elements);
        parseStations(elements);
    }

    private void parseStations(Document elements) {
        Elements stationElements  = elements.select("div.js-metro-stations");
        for (Element element : stationElements) {
            String lineNumber = element.attr("data-line");
            Elements stationList = element.select("p.single-station");
            for (Element station : stationList) {
                String name  = station.select("span.name").text();
                stations.add(new Station(name, lineNumber));
            }
        }
    }

    private void parseLines(Document elements) {
        Elements lineElements = elements.select("span.js-metro-line");
        for (Element element : lineElements) {
            String number = element.attr("data-line");
            String name = element.text();
            lines.add(new Line(number, name));
        }
    }

    public List<Line> getLines() {
        return lines;
    }

    public List<Station> getStations() {
        return stations;
    }

    @Override
    public String toString() {
        return "MetroParser{" +
                "lines=" + lines +
                ", stations=" + stations +
                '}';
    }
}

