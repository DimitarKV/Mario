package entities;

import enums.Origin;
import java.awt.image.BufferedImage;
import types.Vector2;

/**
 * An abstract class representing any entity that can be rendered.
 */
public abstract class AbstractEntity {
    protected Vector2 position;
    protected BufferedImage image;
    protected Integer width = 0;
    protected Integer height = 0;
    protected Integer layer = 0;

    /**
     * Full constructor.
     * @param position of the entity
     * @param origin of the specified position
     * @param image of the entity
     * @param width of the entity
     * @param height of the entity
     */
    public AbstractEntity(Vector2 position, Origin origin,
                          BufferedImage image, Integer width, Integer height) {
        if (origin == Origin.TOP_LEFT) {
            this.position = position;
        } else if (origin == Origin.BOTTOM_LEFT) {
            this.position = position.minus(new Vector2(0, height));
        }
        this.image = image;
        this.width = width;
        this.height = height;
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

    /**
     * A method for checking whether the entity is visible by the specified camera.
     * @param camera to use for check
     * @return whether the camera sees the entity
     */
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
