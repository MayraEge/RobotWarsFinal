package Services;

import Models.MapData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.datasource.lookup.MapDataSourceLookup;

import java.io.IOException;

public class JsonParser {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static MapData[] parseMapData(String json) throws IOException{
        return objectMapper.readValue(json, MapData[].class);
    }
}

