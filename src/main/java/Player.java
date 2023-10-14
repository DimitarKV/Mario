import enums.CollisionSide;
import enums.PlayerStateEnum;
import interfaces.Collidable;
import types.HitBox;
import types.Vector2;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public class Player extends AbstractCollidable {
    private Map<PlayerStateEnum, List<BufferedImage>> sprites;
    private PlayerStateEnum currentState;
    private Integer spriteIndex, direction;


    private final double moveSpeed = 0.5, timeUnit = 1000000;
    private final Integer width, height;
    private Vector2 position, mainVelocity, left, right;

    public Player(Map<PlayerStateEnum, List<BufferedImage>> sprites, Vector2 position, Integer width, Integer height) {
        super(new HitBox(position, new Vector2(width, height)));

        this.sprites = sprites;
        this.position = position;
        this.width = width;
        this.height = height;

        this.mainVelocity = new Vector2(0, 0);
        this.left = new Vector2();
        this.right = new Vector2();
        this.currentState = PlayerStateEnum.STATIONARY_RIGHT;
        this.direction = 1;
        this.spriteIndex = 0;
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
        return this.position.y < 300;
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


        if (!this.canFall() && this.getTotalVelocity().y > 0) {
            this.mainVelocity = new Vector2(this.mainVelocity.x, 0);
            this.position = new Vector2(this.position.x, 300);
        }

//        if (!canMoveUp() && this.getTotalVelocity().y < 0) {
//            this.mainVelocity = new Vector2(this.mainVelocity.x, 0);
//            this.position = new Vector2(this.position.x, 430);
//        }
//
//        if (!canMoveRight() && (this.getTotalVelocity().x > 0)) {
//            this.mainVelocity = new Vector2(0, this.mainVelocity.y);
//            this.right = new Vector2();
//            this.position = new Vector2(960 - this.width, this.position.y);
//        }
//
//        if (!canMoveLeft() && (this.getTotalVelocity().x < 0)) {
//            this.mainVelocity = new Vector2(0, this.mainVelocity.y);
//            this.left = new Vector2();
//            this.position = new Vector2(0, this.position.y);
//        }

        Vector2 newPosition = this.position.plus(this.getTotalVelocity().times((double) delta / timeUnit));
        if (newPosition.x != this.position.x)
            this.direction = (newPosition.x - this.position.x) > 0 ? 1 : -1;
        this.position = newPosition;

        if (canFall()) {
            if (this.right.x > 0)
                this.currentState = PlayerStateEnum.AIRBORNE_RIGHT;
            else
                this.currentState = PlayerStateEnum.AIRBORNE_LEFT;
        } else {
            if (this.getTotalVelocity().equals(this.mainVelocity)) {
                if (this.direction > 0)
                    this.currentState = PlayerStateEnum.STATIONARY_RIGHT;
                else if (this.direction < 0)
                    this.currentState = PlayerStateEnum.STATIONARY_LEFT;
                else
                    this.currentState = PlayerStateEnum.STATIONARY_RIGHT;
            } else {
                if (this.right.x > 0)
                    this.currentState = PlayerStateEnum.MOVING_RIGHT;
                else if (this.left.x < 0)
                    this.currentState = PlayerStateEnum.MOVING_LEFT;
            }
        }

        this.spriteIndex = (int) (System.nanoTime() / 50000000 % this.sprites.get(this.currentState).size());
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
        if (!canFall())
            this.mainVelocity = new Vector2(this.mainVelocity.x, -2 * moveSpeed);
    }

    @Override
    public void collidedWith(Collidable other, CollisionSide side, Vector2 maxAllowed) {
        if (side == CollisionSide.UP) {
            this.position = new Vector2(this.position.x, maxAllowed.y);
            if (this.mainVelocity.y < 0)
                this.mainVelocity = new Vector2(this.mainVelocity.x, 0);
        } else if (side == CollisionSide.DOWN) {
            this.position = new Vector2(this.position.x, maxAllowed.y - this.height);
            if (this.mainVelocity.y > 0)
                this.mainVelocity = new Vector2(this.mainVelocity.x, 0);
        } else if (side == CollisionSide.RIGHT) {
            this.position = new Vector2(maxAllowed.x - this.width, this.position.y);
            if (this.mainVelocity.x > 0) {
                this.mainVelocity = new Vector2(0, this.mainVelocity.y);
            }
            if (this.right.x > 0) {
                this.right = new Vector2();
            }
        } else if (side == CollisionSide.LEFT) {
            this.position = new Vector2(maxAllowed.x, this.position.y);
            if (this.mainVelocity.x < 0) {
                this.mainVelocity = new Vector2(0, this.mainVelocity.y);
            }
            if (this.right.x < 0) {
                this.right = new Vector2();
            }
        }
    }
}
