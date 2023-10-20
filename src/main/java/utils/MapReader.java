package utils;

import com.google.gson.Gson;
import types.MapDescriptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class MapReader {
    public static MapDescriptor readMap(File mapFile) {
        if (!mapFile.canRead())
            return null;

        Gson gson = new Gson();
        try {
            MapDescriptor map = gson.fromJson(new FileReader(mapFile), MapDescriptor.class);
            return map;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
