package entities;

import enums.Origin;
import java.awt.image.BufferedImage;
import types.Vector2;

/**
 * Entity used to make characters win the game when hit.
 */
public class GameOverEntity extends AbstractCollidable {
    public GameOverEntity(Vector2 position, Origin origin, BufferedImage image, Integer width,
                          Integer height, Vector2 hitBoxOffset, Vector2 hitBoxDimensions) {
        super(position, origin, image, width, height, hitBoxOffset, hitBoxDimensions);
    }
}
