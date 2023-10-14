import enums.CollisionSide;
import interfaces.Collidable;
import types.HitBox;

public abstract class AbstractCollidable implements Collidable {
    private HitBox hitBox;

    public AbstractCollidable(HitBox hitBox) {
        this.hitBox = hitBox;
    }

    @Override
    public CollisionSide collidesWith(Collidable other) {
        return this.hitBox.collidesWith(other.getHitBox());
    }

    @Override
    public HitBox getHitBox() {
        return this.hitBox;
    }
}
