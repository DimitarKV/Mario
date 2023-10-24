package entities;

import types.Vector2;

import java.awt.image.BufferedImage;

public class Coin extends AbstractCollidable {
    private final Integer value;
    public Coin(Vector2 position, BufferedImage image, Integer width, Integer height, Vector2 hitBoxOffset, Vector2 hitBoxDimensions, Integer value) {
        super(position, image, width, height, hitBoxOffset, hitBoxDimensions);
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }
}
