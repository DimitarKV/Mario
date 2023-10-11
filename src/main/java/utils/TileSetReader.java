package utils;

import com.google.gson.Gson;
import types.TileSetDescriptor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class TileSetReader {
    public static List<BufferedImage> readTileset(String filePath) {
        File tileSet = new File(filePath);
        if (!tileSet.canRead())
            return null;

        Gson gson = new Gson();
        try {
            TileSetDescriptor descriptor = gson.fromJson(new FileReader(tileSet), TileSetDescriptor.class);
            File image = new File(descriptor.image);
            BufferedImage tileset = ImageIO.read(image);

//            for (int row = 0; row < descriptor.; row++) {
//                for (int col = 0; col < descriptor.columns; col++) {
//
//                }
//            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}
