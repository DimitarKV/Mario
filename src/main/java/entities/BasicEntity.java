package entities;

import enums.Origin;
import types.Vector2;

import java.awt.image.BufferedImage;

public class BasicEntity extends AbstractEntity {
    public BasicEntity(Vector2 position, BufferedImage image, Integer width, Integer height) {
        super(position, Origin.TOP_LEFT, image, width, height);
    }
}
