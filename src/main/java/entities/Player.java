package entities;

import enums.Origin;
import enums.CharacterStateEnum;
import exceptions.CouldNotReadFileException;
import interfaces.Collidable;
import interfaces.Updatable;
import types.Sound;
import types.Vector2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Player extends AbstractCharacter {
    private boolean won = false;
    private Sound sound;

    public Player(String spriteRoot, Vector2 position, Origin origin, Vector2 hitBoxOffset, Vector2 hitBoxDimensions, Integer width, Integer height, Collisions collisions) {
        super(spriteRoot, position, origin, null, width, height, hitBoxOffset, hitBoxDimensions, collisions);
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
            if (this.getHitBox().getBottomLeft().y > other.getHitBox().getTopLeft().y + (other.getHitBox().dimensions.y / 4)) {
                this.dead = true;
            } else {
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
