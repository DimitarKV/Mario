package entities;

import types.Vector2;

import java.awt.image.BufferedImage;

public class BasicCollisionObject extends AbstractCollidable {
    public BasicCollisionObject(Vector2 position, BufferedImage image, Integer width, Integer height, Vector2 hitBoxOffset, Vector2 hitBoxDimensions) {
        super(position, image, width, height, hitBoxOffset, hitBoxDimensions);
    }
}
