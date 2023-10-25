package entities;

import enums.Origin;
import enums.PlayerStateEnum;
import exceptions.CouldNotReadFileException;
import interfaces.Collidable;
import interfaces.Updatable;
import types.Sound;
import types.Vector2;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Player extends AbstractCollidable implements Updatable {
    private final Map<PlayerStateEnum, List<BufferedImage>> sprites;
    private PlayerStateEnum currentState;
    private Integer spriteIndex = 0, spriteSpeed = 100, direction = 1, coinsCount = 0;
    private boolean jump = false;
    private final Collisions collisions;
    private Vector2 mainVelocity, left, right;
    private final double moveSpeed = 0.5, timeUnit = 1000000, gravity = 0.000000003;
    private Sound sound;

    public Player(String root, Vector2 position, Origin origin, Vector2 hitBoxOffset, Vector2 hitBoxDimensions, Integer width, Integer height, Collisions collisions) {
        super(position, origin, null, width, height, hitBoxOffset, hitBoxDimensions);
        this.sprites = new HashMap<>();
        this.position = new Vector2(position.x, position.y - height);

        this.mainVelocity = new Vector2(0, 0);
        this.left = new Vector2();
        this.right = new Vector2();
        this.currentState = PlayerStateEnum.MOVING_RIGHT;
        this.spriteIndex = 0;

        this.collisions = collisions;

        this.sound = new Sound();

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

    @Override
    public void move(Long delta) {
        Vector2 gForce = new Vector2(0, gravity * delta);
        this.mainVelocity = this.mainVelocity.plus(gForce);

        if (jumpPressed)
            jump();

        Vector2 newPosition = this.position.plus(this.getTotalVelocity().times((double) delta / timeUnit));
        Vector2 oldPosition = this.position;

        translateX(newPosition);
        correctX(oldPosition);

        translateY(newPosition);
        correctY(oldPosition);

        calculateSprite(newPosition, oldPosition);

        if (this.mainVelocity.y != 0)
            this.jump = true;

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
        List<Collidable> collidedWith = collisions.checkCollisions(this);

        for (var other : collidedWith) {
            if (!other.isSolid())
                continue;

            if (this.position.x - oldPosition.x > 0) {
                this.position = new Vector2(other.getHitBox().getTopLeft().x - this.getHitBox().dimensions.x - this.hitBoxOffset.x, this.position.y);

            } else if (this.position.x - oldPosition.x < 0) {
                this.position = new Vector2(other.getHitBox().getTopRight().x - this.hitBoxOffset.x, this.position.y);
            }
        }
    }

    private void correctY(Vector2 oldPosition) {
        List<Collidable> collidedWith = collisions.checkCollisions(this);

        for (var other : collidedWith) {
            if (!other.isSolid())
                continue;

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

    private void jump() {
        if (jump)
            return;
        this.jump = true;
        this.mainVelocity = new Vector2(this.mainVelocity.x, -2.7 * moveSpeed);
        sound.setFile("jump");
        sound.play("jump");
    }


    boolean jumpPressed = false;

    public void queueJump() {
        jumpPressed = true;
    }

    public void dequeueJump() {
        jumpPressed = false;
    }

    @Override
    public void collidedWith(Collidable other) {
        if(other instanceof Coin) {
            coinsCount++;
        }
    }

    public Integer getCoinsCount() {
        return coinsCount;
    }
}
