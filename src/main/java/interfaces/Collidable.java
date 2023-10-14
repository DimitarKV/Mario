package interfaces;

import enums.CollisionSide;
import types.HitBox;
import types.Vector2;

public interface Collidable {
    void collidedWith(Collidable other, CollisionSide side, Vector2 maxAllowed);
    // TODO: Expand to take types of collidable objects to execute actions based on collision type
    HitBox getHitBox();

    CollisionSide collidesWith(Collidable collidable);
}
