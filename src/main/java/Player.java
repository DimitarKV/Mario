import types.Vector2;

import java.awt.image.BufferedImage;
import java.util.List;

public class Player {

    private List<BufferedImage> sprites;
    private Integer width, height, currentSprite = 0;
    private Vector2 position, velocity;
    private Vector2 moveLeft, moveRight;
    private final double moveSpeed = 0.5, timeUnit = 1000000;

    public Player(List<BufferedImage> sprites, Vector2 position, Integer width, Integer height) {
        this.sprites = sprites;
        this.position = position;
        this.width = width;
        this.height = height;
        this.currentSprite = 0;
        this.velocity = new Vector2(0, 0);
        this.moveLeft = new Vector2();
        this.moveRight = new Vector2();
    }

    public Vector2 bottomLeft() {
        return new Vector2(this.position.x, this.position.y + this.height);
    }
    public Vector2 bottomRight() {
        return new Vector2(this.position.x + this.width, this.position.y + this.height);
    }
    public Vector2 topRight() { return new Vector2(this.position.x + this.width, this.position.y); }
    public Vector2 topLeft() { return new Vector2(this.position.x, this.position.y); }

    private boolean canFall() {
        return this.position.y < 500;
    }
    private boolean canMoveRight() {
        return this.position.x < 500;
    }

    private boolean canMoveUp() {
        return this.position.y > 430;
    }

    /*TODO
     * COLLISION IDEA
     * Implement colision class with a func to pass two objects to and determine whether they collide
     */

    private boolean canMoveLeft() {
        return this.position.x > 50;
    }

    public void move(Long delta) {

        Vector2 gForce = new Vector2(0, 0.003 * delta / this.timeUnit);
        this.velocity = this.velocity.plus(gForce);

        double dx = this.moveLeft.x + this.moveRight.x;

        if(!this.canFall() && this.velocity.y > 0){
            this.velocity = new Vector2(this.velocity.x, 0);
            this.position = new Vector2(this.position.x, 500);
        }

        if (!canMoveUp() && this.velocity.y < 0){
            this.velocity = new Vector2(this.velocity.x, 0);
            this.position = new Vector2(this.position.x, 430);
        }

        if(!canMoveRight() && (this.velocity.x > 0 || dx > 0)){
            this.velocity = new Vector2(0, this.velocity.y);
            dx = 0;
            this.position = new Vector2(500, this.position.y);
        }

        if(!canMoveLeft() && (this.velocity.x < 0 || dx < 0)){
            this.velocity = new Vector2(0, this.velocity.y);
            dx = 0;
            this.position = new Vector2(50, this.position.y);
        }

        Vector2 newPosition = this.position.plus(this.velocity.times((double)delta/timeUnit)).plus(new Vector2(dx, 0).times((double) delta/timeUnit));

        this.position = newPosition;
    }

    public BufferedImage getCurrentSprite() {
        return sprites.get(currentSprite);
    }

    public void walkLeft() {
        this.moveLeft = new Vector2(-moveSpeed, 0);
    }

    public void walkRight() {
        this.moveRight = new Vector2(moveSpeed, 0);
    }
    public void stopWalkLeft() {
        this.moveLeft = new Vector2();
    }

    public void stopWalkRight() {
        this.moveRight = new Vector2();
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
        if(!canFall())
            this.velocity = new Vector2(this.velocity.x, -2*moveSpeed);
    }
}
