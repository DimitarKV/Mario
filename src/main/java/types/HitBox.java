package types;

import enums.CollisionSide;

import java.awt.*;

public class HitBox {
    public Vector2 topLeft;
    public Vector2 dimensions;

    public HitBox(Vector2 topLeft, Vector2 dimensions) {
        this.topLeft = topLeft;
        this.dimensions = dimensions;
    }

    public Vector2 getTopLeft() {
        return this.topLeft;
    }

    public Vector2 getTopRight() {
        return this.topLeft.plus(new Vector2(this.dimensions.x, 0));
    }

    public Vector2 getBottomLeft() {
        return this.topLeft.plus(new Vector2(0, this.dimensions.y));
    }

    public Vector2 getBottomRight() {
        return this.topLeft.plus(this.dimensions);
    }

    public boolean collidesWith(HitBox other) {
        Rectangle thisRect = new Rectangle((int)this.getTopLeft().x, (int)this.getTopLeft().y, (int)this.dimensions.x, (int)this.dimensions.y);
        Rectangle otherRect = new Rectangle((int)other.getTopLeft().x, (int)other.getTopLeft().y, (int)other.dimensions.x, (int)other.dimensions.y);

        return thisRect.intersects(otherRect);
    }
}
