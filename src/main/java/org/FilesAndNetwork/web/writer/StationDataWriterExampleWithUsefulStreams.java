package org.FilesAndNetwork.web.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.FilesAndNetwork.web.model.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class StationDataWriterExampleWithUsefulStreams {
    private final ObjectMapper mapper = new ObjectMapper();

    public void write(List<Line> lines,
                      List<Station> stations,
                      List<Connection> connections,
                      List<StationDepth> depths,
                      List<StationInfo> dates) throws IOException {

        Map<String, String> linesNumberToName = lines.stream()
                .collect(Collectors.toMap(Line::getNumber, Line::getName));

        Map<String, String> depthMap = depths.stream()
                .collect(Collectors.toMap(
                        StationDepth::getStation_name,
                        StationDepth::getDepth,
                        (existing, replacement) -> existing
                ));

        Map<String, String> dateMap = dates.stream()
                .collect(Collectors.toMap(
                        StationInfo::getName,
                        StationInfo::getDate,
                        (existing, replacement) -> existing
                ));

        Set<String> connectionStations = connections.stream()
                .flatMap(conn -> Optional.ofNullable(conn.getStations())
                        .stream()
                        .flatMap(Collection::stream))
                .filter(Objects::nonNull)
                .map(Station::getName)
                .collect(Collectors.toSet());

        ObjectNode root = mapper.createObjectNode();
        ArrayNode stationsArray = mapper.createArrayNode();

        stations.forEach(station -> {
            ObjectNode stationNode = mapper.createObjectNode();
            stationNode.put("name", station.getName());

            String lineName = linesNumberToName.get(station.getLineNumber());
            if (lineName != null) {
                stationNode.put("line", lineName);
            }

            if (dateMap.containsKey(station.getName())) {
                stationNode.put("date", dateMap.get(station.getName()));
            }

            if (depthMap.containsKey(station.getName())) {
                stationNode.put("depth", depthMap.get(station.getName()));
            }

            stationNode.put("isConnection", connectionStations.contains(station.getName()));

            stationsArray.add(stationNode);
        });
        root.set("stations", stationsArray);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("stations.json"), root);

        ObjectNode mapRoot = mapper.createObjectNode();
        ArrayNode linesArray = mapper.createArrayNode();
        ObjectNode stationsNode = mapper.createObjectNode();

        lines.forEach(line -> {
            ObjectNode lineNode = mapper.createObjectNode();
            lineNode.put("number", line.getNumber());
            lineNode.put("name", line.getName());
            linesArray.add(lineNode);

            ArrayNode stationsOnLine = mapper.createArrayNode();
            stations.stream()
                    .filter(station -> line.getNumber().equals(station.getLineNumber()))
                    .map(Station::getName)
                    .forEach(stationsOnLine::add);

            stationsNode.set(line.getNumber(), stationsOnLine);
        });

        mapRoot.set("lines", linesArray);
        mapRoot.set("stations", stationsNode);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("map.json"), mapRoot);
    }
    }
