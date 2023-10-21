package entities;

import types.Vector2;

import java.awt.image.BufferedImage;

public class BasicEntity extends AbstractEntity {
    public BasicEntity(Vector2 position, BufferedImage image, Integer width, Integer height) {
        super(position, image, width, height);
    }
}
