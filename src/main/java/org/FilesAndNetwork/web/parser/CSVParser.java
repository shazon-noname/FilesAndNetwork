package org.FilesAndNetwork.web.parser;

import org.FilesAndNetwork.web.model.StationInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    public List<StationInfo> parse(String csvData) {
        List<StationInfo> stationInfos = new ArrayList<>();

        try (BufferedReader bufferReader = new BufferedReader(new FileReader(csvData))) {
            String line;
            bufferReader.readLine();

            while ((line = bufferReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String depth = parts[1].trim();
                    stationInfos.add(new StationInfo(name, depth));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stationInfos;
    }
}
