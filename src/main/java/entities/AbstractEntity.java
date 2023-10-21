package entities;

import types.Vector2;

import java.awt.image.BufferedImage;

public abstract class AbstractEntity {
    protected Vector2 position;
    protected BufferedImage image;
    protected Integer width, height, layer = 0;

    public AbstractEntity(Vector2 position, BufferedImage image, Integer width, Integer height) {
        this.position = position;
        this.image = image;
        this.width = width;
        this.height = height;
    }
    public AbstractEntity(Vector2 position, BufferedImage image, Integer width, Integer height, Integer layer) {
        this.position = position;
        this.image = image;
        this.width = width;
        this.height = height;
        this.layer = layer;
    }

    public Vector2 getPosition() {
        return position;
    }


    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Integer getWidth() {
        return width;
    }


    public Integer getHeight() {
        return height;
    }

    public Integer getLayer() {
        return this.layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public boolean isVisible(Camera camera) {
        return camera.contains(this.position.x, this.position.y)
                || camera.contains(this.position.x + width, this.position.y)
                || camera.contains(this.position.x + width, this.position.y + height)
                || camera.contains(this.position.x, this.position.y + height);
    }

    public void setBottomLeft(Vector2 pos) {
        this.position = new Vector2(pos.x, pos.y - this.height);
    }
}
