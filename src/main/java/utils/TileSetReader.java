package utils;

import com.google.gson.Gson;
import types.TileSetDescriptor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;


public class TileSetReader {
    public static List<BufferedImage> readTileset(String filePath) {
        File tileSet = new File(filePath);
        if(!tileSet.canRead())
            return null;

        Gson gson = new Gson();
        try {
            TileSetDescriptor descriptor = gson.fromJson(new FileReader(tileSet), TileSetDescriptor.class);
            File image = new File(descriptor.image);
            
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}
