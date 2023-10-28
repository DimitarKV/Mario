package entities;

import enums.Origin;
import java.awt.image.BufferedImage;
import types.Vector2;

/**
 * A class for entity which can make a character dead.
 */
public class DieEntity extends AbstractCollidable {
    public DieEntity(Vector2 position, Origin origin, BufferedImage image, Integer width,
                     Integer height, Vector2 hitBoxOffset, Vector2 hitBoxDimensions) {
        super(position, origin, image, width, height, hitBoxOffset, hitBoxDimensions);
    }
}
