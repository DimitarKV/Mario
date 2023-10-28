package types;

/**
 * Widely used class in the app representing a two-dimensional vector.
 */
public class Vector2 {
    public final double x;
    public final double y;

    /**
     * Empty constructor initializing a zero vector.
     */
    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Full constructor.
     * @param x component
     * @param y component
     */
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 plus(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    public Vector2 minus(Vector2 other) {
        return new Vector2(this.x - other.x, this.y - other.y);
    }

    public Vector2 times(double scalar) {
        return new Vector2(this.x * scalar, this.y * scalar);
    }

    public boolean equals(Vector2 other) {
        return this.x == other.x && this.y == other.y;
    }
}
