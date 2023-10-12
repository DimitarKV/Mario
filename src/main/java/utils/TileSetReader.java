package utils;

import com.google.gson.Gson;
import types.TileSetDescriptor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TileSetReader {
    public static List<BufferedImage> readTileset(String filePath) {
        List<BufferedImage> output = new ArrayList<>();

        File tileSet = new File(filePath);
        if (!tileSet.canRead())
            return null;

        Gson gson = new Gson();
        try {
            TileSetDescriptor descriptor = gson.fromJson(new FileReader(tileSet), TileSetDescriptor.class);
            File image = new File(tileSet.getParent() + "/" + descriptor.image);
            BufferedImage tileset = ImageIO.read(image);

            for (int tileNum = 0; tileNum < descriptor.tileCount; tileNum++) {
                var newImage = getTile(tileNum, descriptor, tileset);
                output.add(newImage);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return output;
    }

    private static BufferedImage getTile(int tileNumber, TileSetDescriptor descriptor, BufferedImage image) {
        int row = tileNumber / descriptor.columns;
        int col = (tileNumber - row * descriptor.columns);
        int y = row * (descriptor.tileHeight + descriptor.spacing) + descriptor.margin;
        int x = col * (descriptor.tileWidth + descriptor.spacing) + descriptor.margin;

        return image.getSubimage(x, y, descriptor.tileWidth, descriptor.tileHeight);
    }

}
