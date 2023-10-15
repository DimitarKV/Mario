import types.Vector2;

import java.awt.image.BufferedImage;

public class Tile extends AbstractEntity{
    public Tile(Vector2 position, BufferedImage image, Integer width, Integer height) {
        super(position, image, width, height);
    }
}
