package entities;

import interfaces.Collidable;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class that deals with collisions in the app.
 */
public class Collisions {
    private List<Collidable> moving;
    private List<Collidable> stationary;

    /**
     * An empty constructor which just initializes inner containers.
     */
    public Collisions() {
        this.moving = new ArrayList<>();
        this.stationary = new ArrayList<>();
    }

    public void addMovingCollider(Collidable collider) {
        this.moving.add(collider);
    }

    public void addStationaryCollider(Collidable collidable) {
        this.stationary.add(collidable);
    }

    /**
     * check whether the specified collidable collides with anything else in the world.
     * @param collidable to check
     * @return a list of other collidables which are hit. If none, then list is empty.
     */
    public List<Collidable> checkCollisions(Collidable collidable) {
        List<Collidable> collisions = new ArrayList<>();

        if (!collidable.isCollidable()) {
            return collisions;
        }
        for (var other :
                this.moving) {
            if (collidable == other || !other.isCollidable()) {
                continue;
            }
            if (collidable.collidesWith(other)) {
                collidable.collidedWith(other);
                other.collidedWith(collidable);
                collisions.add(other);
            }
        }

        for (var other :
                this.stationary) {
            if (!other.isCollidable()) {
                continue;
            }
            if (collidable.collidesWith(other)) {
                collidable.collidedWith(other);
                other.collidedWith(collidable);
                collisions.add(other);
            }
        }

        return collisions;
    }

    /**
     * Notify the two collidables that they have hit each other on the x-axis.
     * @param first collidable
     * @param second collidable
     */
    public void notifyXCollision(Collidable first, Collidable second) {
        first.xCollision(second);
        second.xCollision(first);
    }

    /**
     * Notify the two collidables that they have hit each other on the y-axis.
     * @param first collidable
     * @param second collidable
     */
    public void notifyYCollision(AbstractCharacter first, Collidable second) {
        first.yCollision(second);
        second.yCollision(first);
    }

}
