package entities;

import entities.AbstractEntity;

import java.awt.*;

public class Camera extends Rectangle {
    private AbstractEntity lock;
    private Integer offsetY;

    public Camera(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void lockX(AbstractEntity entity, int offsetY) {
        this.lock = entity;
        this.offsetY = offsetY;
    }

    public void updatePosition() {
        int newX = this.x;

        if(lock.position.x > this.x + 0.6 * this.width)
            newX = (int)(lock.position.x - 0.6 * this.width);

        if(lock.position.x < this.x + 0.2 * this.width)
            newX = (int)(lock.position.x - 0.2 * this.width);

        this.setLocation(newX, offsetY);
    }


}
