package org.FilesAndNetwork.web.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.FilesAndNetwork.web.model.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class StationDataWriter {

    private final ObjectMapper mapper = new ObjectMapper();

    public void write(List<Line> lines,
                      List<Station> stations,
                      List<Connection> connections,
                      List<StationDepth> depths,
                      List<StationInfo> dates) throws IOException {

        Map<String, String> lineNumberToName = new HashMap<>();
        for (Line line : lines) {
            lineNumberToName.put(line.getNumber(), line.getName());
        }

        Map<String, String> depthMap = new HashMap<>();
        for (StationDepth sd : depths) {
            depthMap.put(sd.getStation_name(), sd.getDepth());
        }

        Map<String, String> dateMap = new HashMap<>();
        for (StationInfo si : dates) {
            dateMap.put(si.getName(), si.getDate());
        }

        Set<String> connectionStations = new HashSet<>();
        for (Connection conn : connections) {
            if (conn.getStation1() != null) {
                connectionStations.add(conn.getStation1().getName());
            }
            if (conn.getStation2() != null) {
                connectionStations.add(conn.getStation2().getName());
            }
        }

        ObjectNode root = mapper.createObjectNode();
        ArrayNode stationsArray = mapper.createArrayNode();

        for (Station station : stations) {
            ObjectNode stationNode = mapper.createObjectNode();

            stationNode.put("name", station.getName());

            String lineName = lineNumberToName.get(station.getLineNumber());
            if (lineName != null) {
                stationNode.put("line", lineName);
            }

            if (dateMap.containsKey(station.getName())) {
                stationNode.put("date", dateMap.get(station.getName()));
            }

            if (depthMap.containsKey(station.getName())) {
                String depthValue = depthMap.get(station.getName());
                if (!depthValue.isEmpty()) {
                    try {
                        stationNode.put("depth", Double.parseDouble(depthValue));
                    } catch (NumberFormatException e) {
                        stationNode.put("depth", depthValue);
                    }
                }
            }
            stationNode.put("hasConnection", connectionStations.contains(station.getName()));

            stationsArray.add(stationNode);
        }

        root.set("stations", stationsArray);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("stations.json"), root);

        ObjectNode mapRoot = mapper.createObjectNode();
        ArrayNode linesArray = mapper.createArrayNode();
        ObjectNode stationsObject = mapper.createObjectNode();

        for (Line line : lines) {
            ObjectNode lineNode = mapper.createObjectNode();
            lineNode.put("number", line.getNumber());
            lineNode.put("name", line.getName());
            linesArray.add(lineNode);

            ArrayNode stationNames = mapper.createArrayNode();
            for (Station station : stations) {
                if (station.getLineNumber().equals(line.getNumber())) {
                    stationNames.add(station.getName());
                }
            }
            stationsObject.set(line.getNumber(), stationNames);
        }

        mapRoot.set("lines", linesArray);
        mapRoot.set("stations", stationsObject);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("map.json"), mapRoot);
    }
}