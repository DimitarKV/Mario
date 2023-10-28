package entities;

import enums.Origin;
import interfaces.Collidable;
import types.Sound;
import types.Vector2;

/**
 * A class for the player.
 */
public class Player extends AbstractCharacter {
    private boolean won = false;
    private Sound sound;

    /**
     * Constructor for the player.
     * @param spriteRoot of the player
     * @param position of the player
     * @param origin of the specified position
     * @param hitBoxOffset with respect to top left
     * @param hitBoxDimensions of the hit box of the player
     * @param width of the rendered image
     * @param height of the rendered image
     * @param collisions detects collision
     */
    public Player(String spriteRoot, Vector2 position, Origin origin, Vector2 hitBoxOffset,
                  Vector2 hitBoxDimensions, Integer width, Integer height, Collisions collisions) {
        super(spriteRoot, position, origin, null, width, height,
                hitBoxOffset, hitBoxDimensions, collisions);
        this.sound = new Sound();
    }

    @Override
    protected boolean jump() {
        boolean result = super.jump();
        if (result) {
            sound.setFile("jump");
            sound.play("jump");
        }
        return result;
    }

    @Override
    public void collidedWith(Collidable other) {
        if (other instanceof Coin) {
            coinsCount++;
        } else if (other instanceof GameOverEntity) {
            won = true;
        } else if (other instanceof DieEntity) {
            dead = true;
        } else if (other instanceof Enemy) {
            if (this.getHitBox().getBottomLeft().y > other.getHitBox().getTopLeft().y
                    + (other.getHitBox().dimensions.y / 4)) {
                this.dead = true;
            } else {
                sound.setFile("kick");
                sound.play("kick");
                this.coinsCount += 69;
                this.jumpOnce();
            }
        }
    }

    public Integer getCoinsCount() {
        return coinsCount;
    }

    public boolean isWon() {
        return this.won;
    }

    public boolean isDead() {
        return this.dead;
    }
}
