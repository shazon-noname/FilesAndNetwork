package org.FilesAndNetwork.web.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.FilesAndNetwork.web.model.StationDepth;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonParser {
    private final ObjectMapper mapper = new ObjectMapper();

    public List<StationDepth> parse(File jsonFile) throws IOException {
        return mapper.readValue(jsonFile,
                new TypeReference<>() {});
    }
}