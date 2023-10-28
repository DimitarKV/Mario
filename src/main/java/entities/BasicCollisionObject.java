package entities;

import enums.Origin;
import java.awt.image.BufferedImage;
import types.Vector2;

/**
 * A basic class for objects that need to have the physical properties of a solid object,
 * but don't need to move or have some serious interaction with the world.
 */
public class BasicCollisionObject extends AbstractCollidable {
    public BasicCollisionObject(Vector2 position, Origin origin, BufferedImage image,
                                Integer width, Integer height, Vector2 hitBoxOffset,
                                Vector2 hitBoxDimensions) {
        super(position, origin, image, width, height, hitBoxOffset, hitBoxDimensions);
    }
}
