package interfaces;

import types.HitBox;
import types.Vector2;

public interface Collidable {
    HitBox getHitBox();
    void setHitBox(Vector2 hitBoxOffset, Vector2 hitBoxDimensions);
    boolean isCollidable();
    boolean collidesWith(Collidable collidable);
    void collidedWith(Collidable other);
}
