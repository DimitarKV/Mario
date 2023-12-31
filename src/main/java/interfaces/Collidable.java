package interfaces;

import types.HitBox;
import types.Vector2;

/**
 * An interface representing an entity with collision capabilities.
 */
public interface Collidable {
    HitBox getHitBox();

    void setHitBox(Vector2 hitBoxOffset, Vector2 hitBoxDimensions);

    boolean isCollidable();

    boolean isSolid();

    boolean collidesWith(Collidable collidable);

    void collidedWith(Collidable other);

    void xCollision(Collidable other);

    void yCollision(Collidable other);
}
