package types;

import enums.CollisionSide;

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

    public boolean isPointIn(Vector2 point) {
        return this.getTopLeft().x <= point.x && this.getTopLeft().y <= point.y && this.getBottomRight().x >= point.x && this.getBottomRight().y >= point.y;
    }

    public CollisionSide collidesWith(HitBox other) {
        if (other.isPointIn(this.getTopRight()) && other.isPointIn(this.getBottomRight())) {
            return CollisionSide.RIGHT;
        } else if (other.isPointIn(this.getBottomLeft()) && other.isPointIn(this.getBottomRight())) {
            return CollisionSide.DOWN;
        } else if (other.isPointIn(this.getTopLeft()) && other.isPointIn(this.getBottomLeft())) {
            return CollisionSide.LEFT;
        } else if (other.isPointIn(this.getTopLeft()) && other.isPointIn(this.getTopRight())) {
            return CollisionSide.UP;
        }
        return CollisionSide.NONE;
    }
}
