package types;

/**
 * A class representing the hit box of an entity (i.e. its physical borders in the app world).
 */
public class HitBox {
    public Vector2 topLeft;
    public Vector2 dimensions;

    /**
     * A full constructor.
     * @param topLeft of the hit box
     * @param dimensions of the hit box
     */
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

    /**
     * Determines whether the current hit box collides with the other.
     * @param other hit box
     * @return whether they collide
     */
    public boolean collidesWith(HitBox other) {
        double tw = this.dimensions.x;
        double th = this.dimensions.y;
        double rw = other.dimensions.x;
        double rh = other.dimensions.y;
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        double tx = this.topLeft.x;
        double ty = this.topLeft.y;
        double rx = other.topLeft.x;
        double ry = other.topLeft.y;
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;
        //      overflow || intersect
        return ((rw < rx || rw > tx)
                && (rh < ry || rh > ty)
                && (tw < tx || tw > rx)
                && (th < ty || th > ry));

    }
}
