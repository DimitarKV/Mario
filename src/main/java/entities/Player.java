package entities;

import entities.AbstractCollidable;
import entities.Collisions;
import enums.PlayerStateEnum;
import interfaces.Collidable;
import types.Vector2;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public class Player extends AbstractCollidable {
    private Map<PlayerStateEnum, List<BufferedImage>> sprites;
    private PlayerStateEnum currentState;
    private Integer spriteIndex, direction;


    private final double moveSpeed = 0.5, timeUnit = 1000000;
    private Vector2 mainVelocity, left, right;
    private Collisions collisions;

    public Player(Map<PlayerStateEnum, List<BufferedImage>> sprites, Vector2 position, Integer width, Integer height, Collisions collisions) {
        super(position, null, width, height, new Vector2(), new Vector2(width, height));

        this.sprites = sprites;
        this.position = position;

        this.mainVelocity = new Vector2(0, 0);
        this.left = new Vector2();
        this.right = new Vector2();
        this.currentState = PlayerStateEnum.STATIONARY_RIGHT;
        this.direction = 1;
        this.spriteIndex = 0;

        this.collisions = collisions;
    }

    public Vector2 getTotalVelocity() {
        return this.mainVelocity.plus(this.left).plus(this.right);
    }

    public Vector2 bottomLeft() {
        return new Vector2(this.position.x, this.position.y + this.height);
    }

    public Vector2 bottomRight() {
        return new Vector2(this.position.x + this.width, this.position.y + this.height);
    }

    public Vector2 topRight() {
        return new Vector2(this.position.x + this.width, this.position.y);
    }

    public Vector2 topLeft() {
        return new Vector2(this.position.x, this.position.y);
    }

    private boolean canFall() {
        return this.position.y < 960 - this.height;
    }

    private boolean canMoveRight() {
        return this.position.x < 960 - this.width;
    }

    private boolean canMoveUp() {
        return this.position.y > 130;
    }

    private boolean canMoveLeft() {
        return this.position.x > 0;
    }

    /*TODO
     * COLLISION IDEA
     * Implement collision class with a func to pass two objects to and determine whether they collide
     */


    public void move(Long delta) {
        double gravity = 0.000000003;
        Vector2 gForce = new Vector2(0, gravity * delta);
        this.mainVelocity = this.mainVelocity.plus(gForce);

        Vector2 newPosition = this.position.plus(this.getTotalVelocity().times((double) delta / timeUnit));
        Vector2 oldPosition = this.position;
        translateX(newPosition);
        correctX(oldPosition);

        translateY(newPosition);
        correctY(oldPosition);

       this.setImage(this.sprites.get(this.currentState).get(this.spriteIndex));
    }

    private void translateX(Vector2 newPosition) {
        this.position = new Vector2(newPosition.x, this.position.y);
    }

    private void translateY(Vector2 newPosition) {
        this.position = new Vector2(this.position.x, newPosition.y);
    }

    private void correctX(Vector2 oldPosition) {
        Collidable other = collisions.checkCollisions(this);
        if (other != null){
            if (this.position.x - oldPosition.x > 0) {
                this.position = new Vector2(other.getHitBox().getTopLeft().x - this.width, this.position.y);
                this.mainVelocity = new Vector2(0, this.mainVelocity.y);
                this.right = new Vector2();
            } else if (this.position.x - oldPosition.x < 0) {
                this.position = new Vector2(other.getHitBox().getTopRight().x, this.position.y);
                this.mainVelocity = new Vector2(0, this.mainVelocity.y);
                this.left = new Vector2();
            }
        }
    }

    private void correctY(Vector2 oldPosition) {
        Collidable other = collisions.checkCollisions(this);
        if (other != null){
            if (this.position.y - oldPosition.y > 0) {
                this.position = new Vector2(this.position.x, other.getHitBox().getTopLeft().y - this.height);
                this.mainVelocity = new Vector2(this.mainVelocity.x, 0);
            } else if (this.position.y - oldPosition.y < 0) {
                this.position = new Vector2(this.position.x, other.getHitBox().getBottomRight().y);
                this.mainVelocity = new Vector2(this.mainVelocity.x, 0);
            }
        }
    }

    public BufferedImage getCurrentSprite() {
        return sprites.get(currentState).get(spriteIndex);
    }

    public void walkLeft() {
        this.left = new Vector2(-moveSpeed, 0);
    }

    public void walkRight() {
        this.right = new Vector2(moveSpeed, 0);
    }

    public void stopWalkLeft() {
        this.left = new Vector2();
    }

    public void stopWalkRight() {
        this.right = new Vector2();
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
        this.mainVelocity = new Vector2(this.mainVelocity.x, -2.5 * moveSpeed);
    }

}
