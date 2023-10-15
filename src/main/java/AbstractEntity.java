import types.Vector2;

import java.awt.image.BufferedImage;

public class AbstractEntity {
    protected Vector2 position;
    protected BufferedImage image;
    protected Integer width, height;

    public AbstractEntity(Vector2 position, BufferedImage image, Integer width, Integer height) {
        this.position = position;
        this.image = image;
        this.width = width;
        this.height = height;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
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

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public boolean isVisible(Camera camera) {
        return camera.contains(this.position.x, this.position.y)
                || camera.contains(this.position.x + width, this.position.y)
                || camera.contains(this.position.x + width, this.position.y + height)
                || camera.contains(this.position.x, this.position.y + height);
    }
}
