package entities;

import enums.Origin;
import java.awt.image.BufferedImage;
import types.Vector2;

/**
 * A basic implementation of the abstract class.
 */
public class BasicEntity extends AbstractEntity {
    public BasicEntity(Vector2 position, BufferedImage image,
                       Integer width, Integer height) {
        super(position, Origin.TOP_LEFT, image, width, height);
    }
}
