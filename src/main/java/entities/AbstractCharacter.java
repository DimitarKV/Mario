package entities;

import enums.CharacterStateEnum;
import enums.Origin;
import exceptions.CouldNotReadFileException;
import interfaces.Collidable;
import interfaces.Updatable;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import types.Vector2;

/**
 * The main abstract class to represent a character in the game.
 */
public abstract class AbstractCharacter extends AbstractCollidable implements Updatable {
    protected final Map<CharacterStateEnum, List<BufferedImage>> sprites = new HashMap<>();
    protected CharacterStateEnum currentState = CharacterStateEnum.STATIONARY_RIGHT;
    protected Integer spriteIndex = 0;
    protected Integer spriteSpeed = 100;
    protected Integer direction = 1;
    protected Integer coinsCount = 0;
    protected boolean jumpPressed = false;
    protected boolean jumpOnce = false;
    protected boolean jump = false;
    protected boolean dead = false;
    protected final Collisions collisions;
    protected Vector2 mainVelocity = new Vector2();
    protected Vector2 left = new Vector2();
    protected Vector2 right = new Vector2();
    protected double moveSpeed = 0.5;
    protected double timeUnit = 1000000;
    protected double gravity = 0.000000003;

    /**
     * A full constructor.
     *
     * @param spriteRoot       the path to the root of sprites for the character
     * @param position         the initial position of the character
     * @param origin           the origin of the supplied position
     * @param image            the current image used as a sprite
     * @param width            the width of the character to be rendered
     * @param height           the height of the character to be rendered
     * @param hitBoxOffset     the offset of the hit box with respect to the upper left corner
     * @param hitBoxDimensions the dimensions of its hit box
     * @param collisions       the Collisions class instance to use for collision detection
     */
    public AbstractCharacter(String spriteRoot,
                             Vector2 position,
                             Origin origin,
                             BufferedImage image,
                             Integer width,
                             Integer height,
                             Vector2 hitBoxOffset,
                             Vector2 hitBoxDimensions,
                             Collisions collisions) {
        super(position, origin, image, width, height, hitBoxOffset, hitBoxDimensions);
        this.collisions = collisions;

        loadSprites(spriteRoot);
    }

    /**
     * Method for loading the sprites of the character.
     *
     * @param root the path to the root of the sprite folder
     */
    protected void loadSprites(String root) {
        try {
            File rootFolder = new File(root);
            if (!rootFolder.canRead()) {
                throw new CouldNotReadFileException("Could not load sprites at [" + root + "]");
            }

            for (var state : CharacterStateEnum.values()) {
                sprites.put(state, new ArrayList<>());
                File stateFolder = rootFolder.toPath().resolve(state.name()).toFile();
                if (stateFolder.listFiles() == null) {
                    continue;
                }
                List<File> children =
                        new ArrayList<>(Arrays.stream(stateFolder.listFiles()).toList());
                children.sort(Comparator.comparing(File::getName));
                for (File child : children) {
                    sprites.get(state).add(ImageIO.read(child));
                }
            }
        } catch (IOException e) {
            throw new CouldNotReadFileException(e.getMessage());
        }
    }

    /**
     * A method used for calculating the current sprite of the character based on the current time,
     * so a smooth animation can occur.
     *
     * @param newPosition of the character
     * @param oldPosition of the character
     */
    protected void calculateSprite(Vector2 newPosition, Vector2 oldPosition) {
        if (newPosition.equals(oldPosition)) {
            return;
        }
        if (this.getTotalVelocity().x != 0) {
            this.direction = this.getTotalVelocity().x > 0 ? 1 : -1;
        }
        if (jump) {
            if (this.direction > 0) {
                this.currentState = CharacterStateEnum.AIRBORNE_RIGHT;
            } else {
                this.currentState = CharacterStateEnum.AIRBORNE_LEFT;
            }
        } else {
            if (this.getTotalVelocity().x == 0) {
                if (this.direction > 0) {
                    this.currentState = CharacterStateEnum.STATIONARY_RIGHT;
                } else {
                    this.currentState = CharacterStateEnum.STATIONARY_LEFT;
                }
            } else {
                if (this.direction > 0) {
                    this.currentState = CharacterStateEnum.MOVING_RIGHT;
                } else {
                    this.currentState = CharacterStateEnum.MOVING_LEFT;
                }
            }
        }


        this.spriteIndex = (int) ((System.currentTimeMillis() / this.spriteSpeed)
                % this.sprites.get(this.currentState).size());
    }

    protected void translateX(Vector2 newPosition) {
        this.position = new Vector2(newPosition.x, this.position.y);
    }

    protected void translateY(Vector2 newPosition) {
        this.position = new Vector2(this.position.x, newPosition.y);
    }

    /**
     * A method for correcting the character's position depending on the collision detected.
     *
     * @param oldPosition the position of the character in the previous frame
     */
    protected void correctX(Vector2 oldPosition) {
        List<Collidable> collidedWith = collisions.checkCollisions(this);

        for (var other : collidedWith) {
            if (!other.isSolid()) {
                continue;
            }

            if (this.position.x - oldPosition.x > 0) {
                this.position = new Vector2(
                        other.getHitBox().getTopLeft().x
                                - this.getHitBox().dimensions.x
                                - this.hitBoxOffset.x,
                        this.position.y);
            } else if (this.position.x - oldPosition.x < 0) {
                this.position = new Vector2(
                        other.getHitBox().getTopRight().x
                                - this.hitBoxOffset.x,
                        this.position.y);
            }
        }

        for (var collisionElement : collidedWith) {
            if (collisionElement.isSolid()) {
                collisions.notifyXCollision(this, collisionElement);
            }
        }
    }

    /**
     * A method for correcting the character's position depending on the collision detected.
     * @param oldPosition the position of the character in the previous frame
     */
    protected void correctY(Vector2 oldPosition) {
        List<Collidable> collidedWith = collisions.checkCollisions(this);

        for (var other : collidedWith) {
            if (!other.isSolid()) {
                continue;
            }

            if (this.position.y - oldPosition.y > 0) {
                this.position = new Vector2(
                        this.position.x,
                        other.getHitBox().getTopLeft().y
                                - this.hitBoxDimensions.y
                                - this.hitBoxOffset.y);
                this.mainVelocity = new Vector2(this.mainVelocity.x, 0);
                this.jump = false;
            } else if (this.position.y - oldPosition.y < 0) {
                this.position = new Vector2(
                        this.position.x,
                        other.getHitBox().getBottomRight().y
                        - this.hitBoxOffset.y);
                this.mainVelocity = new Vector2(this.mainVelocity.x, 0);
            }
        }

        for (var collisionElement : collidedWith) {
            if (collisionElement.isSolid()) {
                collisions.notifyYCollision(this, collisionElement);
            }
        }
    }

    @Override
    public void move(Long delta) {
        if (dead) {
            return;
        }

        Vector2 gForce = new Vector2(0, gravity * delta);
        this.mainVelocity = this.mainVelocity.plus(gForce);

        if (jumpPressed) {
            jump();
        }

        Vector2 newPosition =
                this.position.plus(this.getTotalVelocity().times((double) delta / timeUnit));
        Vector2 oldPosition = this.position;

        translateX(newPosition);
        correctX(oldPosition);

        translateY(newPosition);
        correctY(oldPosition);

        calculateSprite(newPosition, oldPosition);

        if (this.mainVelocity.y != 0) {
            this.jump = true;
        }

        this.setImage(this.sprites.get(this.currentState).get(this.spriteIndex));
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

    public Vector2 getTotalVelocity() {
        return this.mainVelocity.plus(this.left).plus(this.right);
    }

    /**
     * This method is used by ancestors to inevitably make the character jump.
     */
    protected void jumpOnce() {
        this.jumpOnce = true;
        this.queueJump();
    }

    /**
     * This method is used for normal jumping if the character can jump.
     * @return whether a jump happened.
     */
    protected boolean jump() {
        if (jump && !this.jumpOnce) {
            return false;
        }
        this.jump = true;
        if (this.jumpOnce) {
            dequeueJump();
            this.jumpOnce = false;
        }
        this.mainVelocity = new Vector2(this.mainVelocity.x, -2.7 * moveSpeed);
        return true;
    }

    public void queueJump() {
        jumpPressed = true;
    }

    public void dequeueJump() {
        jumpPressed = false;
    }

    @Override
    public void collidedWith(Collidable other) {
        if (other instanceof DieEntity) {
            this.dead = true;
        }
    }

    @Override
    public boolean isActive() {
        return !this.dead;
    }
}
