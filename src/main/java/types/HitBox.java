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
        var topLeft = this.getTopLeft();
        var bottomRight = this.getBottomRight();

        return topLeft.x <= point.x && topLeft.y <= point.y && bottomRight.x >= point.x && bottomRight.y >= point.y;
    }

    public boolean collidesWith(HitBox other) {
        if (other.isPointIn(this.getTopLeft()) ||
                other.isPointIn(this.getTopRight()) ||
                other.isPointIn(this.getBottomLeft()) ||
                other.isPointIn(this.getBottomRight())) {
            return true;
        }
        return false;
    }
}
