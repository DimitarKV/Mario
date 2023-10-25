package entities;

import enums.Origin;
import types.Vector2;

import java.awt.image.BufferedImage;

public class GameOverEntity extends AbstractCollidable {
    public GameOverEntity(Vector2 position, Origin origin, BufferedImage image, Integer width, Integer height, Vector2 hitBoxOffset, Vector2 hitBoxDimensions) {
        super(position, origin, image, width, height, hitBoxOffset, hitBoxDimensions);
    }
}
