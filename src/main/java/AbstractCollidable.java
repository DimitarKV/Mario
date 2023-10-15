import enums.CollisionSide;
import interfaces.Collidable;
import types.HitBox;
import types.Vector2;

import java.awt.image.BufferedImage;

public abstract class AbstractCollidable extends AbstractEntity implements Collidable {
    protected Vector2 hitBoxOffset;
    protected Vector2 hitBoxDimensions;

    public AbstractCollidable(Vector2 position, BufferedImage image, Integer width, Integer height, Vector2 hitBoxOffset, Vector2 hitBoxDimensions) {
        super(position, image, width, height);
        this.hitBoxOffset = hitBoxOffset;
        this.hitBoxDimensions = hitBoxDimensions;
    }

    @Override
    public HitBox getHitBox() {
        return new HitBox(this.position.plus(this.hitBoxOffset), this.hitBoxDimensions);
    }


    @Override
    public boolean collidesWith(Collidable other) {
        return this.getHitBox().collidesWith(other.getHitBox());
    }


    @Override
    public void setHitBox(Vector2 hitBoxOffset, Vector2 hitBoxDimensions) {
        this.hitBoxOffset = hitBoxOffset;
        this.hitBoxDimensions = hitBoxDimensions;
    }
}