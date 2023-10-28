package entities;

import java.awt.*;

/**
 * The camera entity to use for frame calculations.
 */
public class Camera extends Rectangle {
    private AbstractEntity lock;
    private Integer offsetY;

    public Camera(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    /**
     * Lock the position of the camera onto an entity with a specific Y offset.
     * @param entity to lock onto
     * @param offsetY camera
     */
    public void lockX(AbstractEntity entity, int offsetY) {
        this.lock = entity;
        this.offsetY = offsetY;
    }

    /**
     * used to update the position of the camera if the lock has changed position.
     */
    public void updatePosition() {
        int newX = this.x;

        if (lock.position.x > this.x + 0.6 * this.width) {
            newX = (int) (lock.position.x - 0.6 * this.width);
        }
        if (lock.position.x < this.x + 0.2 * this.width) {
            newX = (int) (lock.position.x - 0.2 * this.width);
        }
        this.setLocation(newX, offsetY);
    }
}
