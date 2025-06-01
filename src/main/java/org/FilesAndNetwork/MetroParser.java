package org.FilesAndNetwork;

import org.FilesAndNetwork.model.Line;
import org.FilesAndNetwork.model.Station;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;


public class MetroParser {
    private List<Line> lines;
    private List<Station> stations;

    public static void main(String[] args) throws IOException {
        Document elements = Jsoup.connect("https://skillbox-java.github.io/").get();
        Elements stations = elements.getElementsByClass("js-metro-stations t-metrostation-list-table");
        Elements numberofLines = elements.select("span.js-metro-line");

        for (Element element : numberofLines) {
            String lineNumber = element.attr("data-line");
            String lineName = element.text();
            System.out.println(lineNumber + ": " + lineName);
        }

        for (Element element : stations) {
            String line = element.text();
            System.out.println(line);
        }
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
}

