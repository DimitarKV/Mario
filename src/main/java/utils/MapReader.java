package utils;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import types.MapDescriptor;

/**
 * The class for reading a map from memory.
 */
public class MapReader {
    /**
     * The main static method for reading the map.
     */
    public static MapDescriptor readMap(File mapFile) {
        if (!mapFile.canRead()) {
            return null;
        }

        Gson gson = new Gson();
        try {
            return gson.fromJson(new FileReader(mapFile), MapDescriptor.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
