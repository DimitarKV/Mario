import types.Vector2;

import java.awt.image.BufferedImage;
import java.util.List;

public class Player {

    private List<BufferedImage> sprites;
    private Integer width, height, currentSprite;
    private Vector2 position, velocity;

    public Player(List<BufferedImage> sprites, Vector2 position, Integer width, Integer height, Integer currentSprite, Vector2 velocity) {
        this.sprites = sprites;
        this.position = position;
        this.width = width;
        this.height = height;
        this.currentSprite = currentSprite;
        this.velocity = velocity;
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
        this.position = this.position.plus(velocity.times((double)delta/1000));

    }
    public Vector2 wouldMove(Long delta) {
        return this.position.plus(velocity.times((double)delta/1000));
    }

    public BufferedImage getCurrentSprite() {
        return sprites.get(currentSprite);
    }

}
