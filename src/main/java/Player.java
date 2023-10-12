import types.Vector2;

import java.awt.image.BufferedImage;
import java.util.List;

public class Player {

    private List<BufferedImage> sprites;
    private Integer width, height, currentSprite = 0;
    private Vector2 position, velocity, movingLeft, movingRight;

    public Player(List<BufferedImage> sprites, Vector2 position, Integer width, Integer height) {
        this.sprites = sprites;
        this.position = position;
        this.width = width;
        this.height = height;
        this.currentSprite = 0;
        this.velocity = new Vector2(0, 0);
        this.movingLeft = new Vector2(0, 0);
        this.movingRight = new Vector2(0, 0);
    }

    public Vector2 bottomLeft() {
        return new Vector2(this.position.x, this.position.y + this.height);
    }
    public Vector2 bottomRight() {
        return new Vector2(this.position.x + this.width, this.position.y + this.height);
    }
    public Vector2 topRight() { return new Vector2(this.position.x + this.width, this.position.y); }
    public Vector2 topLeft() { return new Vector2(this.position.x, this.position.y); }
    public void move(Long delta) {
        Vector2 g = new Vector2(0, 0.5);
        this.velocity = this.velocity.plus(g.times((double)delta/50000000));

        this.position = this.position.plus(this.getVelocity().times((double)delta/500000));

    }

    public BufferedImage getCurrentSprite() {
        return sprites.get(currentSprite);
    }

    public void walkLeft() {
        this.movingLeft = new Vector2(-1, 0);
    }

    public void walkRight() {
        this.movingRight = new Vector2(1, 0);
    }

    public Vector2 getVelocity () {
        return this.velocity.plus(movingLeft).plus(movingRight);
    }

    public void stopWalkLeft() {
        this.movingLeft = new Vector2(0, 0);
    }

    public void stopWalkRight() {
        this.movingRight = new Vector2(0, 0);
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public Integer getWidth() {
        return this.width;
    }

    public Integer getHeight() {
        return this.height;
    }

    public void jump() {
        this.velocity = new Vector2(this.velocity.x, -1);
    }
}
