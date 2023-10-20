package entities;

import entities.AbstractCollidable;
import entities.Collisions;
import enums.PlayerStateEnum;
import exceptions.CouldNotReadFileException;
import interfaces.Collidable;
import types.Vector2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Player extends AbstractCollidable {
    private Map<PlayerStateEnum, List<BufferedImage>> sprites;
    private PlayerStateEnum currentState;
    private Integer spriteIndex = 0, spriteSpeed = 100, direction = 1;
    private boolean jump = false;


    private final double moveSpeed = 0.5, timeUnit = 1000000;
    private Vector2 mainVelocity, left, right;
    private Collisions collisions;

    public Player(String root, Vector2 position, Vector2 hitBoxOffset, Vector2 hitBoxDimensions, Integer width, Integer height, Collisions collisions) {
        super(position, null, width, height, hitBoxOffset, hitBoxDimensions);
        this.sprites = new HashMap<>();
        this.position = new Vector2(position.x, position.y - height);

        this.mainVelocity = new Vector2(0, 0);
        this.left = new Vector2();
        this.right = new Vector2();
        this.currentState = PlayerStateEnum.MOVING_RIGHT;
        this.spriteIndex = 0;

        this.collisions = collisions;

        loadSprites(root);
    }

    private void loadSprites(String root) {
        try {
            File rootFolder = new File(root);
            if (!rootFolder.canRead())
                throw new CouldNotReadFileException("Could not load sprites at [" + root + "]");

            for (var state : PlayerStateEnum.values()) {
                sprites.put(state, new ArrayList<>());
                File stateFolder = rootFolder.toPath().resolve(state.name()).toFile();
                if (stateFolder.listFiles() == null)
                    throw new CouldNotReadFileException("Could not resolve [" + stateFolder.getPath() + "]'s children files.");
                List<File> children = new ArrayList<>(Arrays.stream(stateFolder.listFiles()).toList());
//                Collections.sort(children);
                children.sort(Comparator.comparing(File::getName));
                for (File child : children) {
                    sprites.get(state).add(ImageIO.read(child));
                }
            }
        } catch (IOException e) {
            throw new CouldNotReadFileException(e.getMessage());
        }
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

        calculateSprite(newPosition, oldPosition);

        this.setImage(this.sprites.get(this.currentState).get(this.spriteIndex));
    }

    private void calculateSprite(Vector2 newPosition, Vector2 oldPosition) {
        if (newPosition.equals(oldPosition))
            return;

        if (this.getTotalVelocity().x != 0)
            this.direction = this.getTotalVelocity().x > 0 ? 1 : -1;
        if (jump) {
            if (this.direction > 0)
                this.currentState = PlayerStateEnum.AIRBORNE_RIGHT;
            else
                this.currentState = PlayerStateEnum.AIRBORNE_LEFT;
        } else {
            if (this.getTotalVelocity().x == 0) {

                if (this.direction > 0)
                    this.currentState = PlayerStateEnum.STATIONARY_RIGHT;
                else
                    this.currentState = PlayerStateEnum.STATIONARY_LEFT;

            } else {

                if (this.direction > 0)
                    this.currentState = PlayerStateEnum.MOVING_RIGHT;
                else
                    this.currentState = PlayerStateEnum.MOVING_LEFT;
            }
        }


        this.spriteIndex = (int) ((System.currentTimeMillis() / this.spriteSpeed) % this.sprites.get(this.currentState).size());
    }

    private void translateX(Vector2 newPosition) {
        this.position = new Vector2(newPosition.x, this.position.y);
    }

    private void translateY(Vector2 newPosition) {
        this.position = new Vector2(this.position.x, newPosition.y);
    }

    private void correctX(Vector2 oldPosition) {
        Collidable other = collisions.checkCollisions(this);
        if (other != null) {
            if (this.position.x - oldPosition.x > 0) {
                this.position = new Vector2(other.getHitBox().getTopLeft().x - this.getHitBox().dimensions.x - this.hitBoxOffset.x, this.position.y);
//                this.mainVelocity = new Vector2(0, this.mainVelocity.y);
//                this.right = new Vector2();
            } else if (this.position.x - oldPosition.x < 0) {
                this.position = new Vector2(other.getHitBox().getTopRight().x - this.hitBoxOffset.x, this.position.y);
//                this.mainVelocity = new Vector2(0, this.mainVelocity.y);
//                this.left = new Vector2();
            }
        }
    }

    private void correctY(Vector2 oldPosition) {
        Collidable other = collisions.checkCollisions(this);
        if (other != null) {
            if (this.position.y - oldPosition.y > 0) {
                this.position = new Vector2(this.position.x, other.getHitBox().getTopLeft().y - this.hitBoxDimensions.y - this.hitBoxOffset.y);
                this.mainVelocity = new Vector2(this.mainVelocity.x, 0);
                this.jump = false;
            } else if (this.position.y - oldPosition.y < 0) {
                this.position = new Vector2(this.position.x, other.getHitBox().getBottomRight().y - this.hitBoxOffset.y);
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
        if(jump)
            return;
        this.jump = true;
        this.mainVelocity = new Vector2(this.mainVelocity.x, -2.7 * moveSpeed);
    }

    public String getState() {
        return this.currentState.name();
    }
}
