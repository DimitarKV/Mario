package entities;

import enums.Origin;
import interfaces.Collidable;
import interfaces.Updatable;
import java.awt.image.BufferedImage;
import types.Vector2;

/**
 * A class used for coins in the game which is collidable but not solid.
 */
public class Coin extends AbstractCollidable implements Updatable {
    private final Integer value;
    private final Integer timeUnit = 1000000;
    private boolean animation = false;
    private boolean isActive = true;
    private final double gravity = -0.000000003;
    private Vector2 velocity = new Vector2();

    /**
     * Full constructor.
     * @param position of the coin
     * @param origin of the specified position
     * @param image of the coin
     * @param width of the rendered image
     * @param height of the rendered image
     * @param hitBoxOffset with respect to top left
     * @param hitBoxDimensions of the hit box of the coin
     * @param value of the coin
     */
    public Coin(Vector2 position, Origin origin, BufferedImage image, Integer width,
                Integer height, Vector2 hitBoxOffset, Vector2 hitBoxDimensions, Integer value) {
        super(position, origin, image, width, height, hitBoxOffset, hitBoxDimensions);
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public void collidedWith(Collidable other) {
        if (other instanceof Player) {
            this.setCollidable(false);
            this.animation = true;
            this.setLayer(10);
        }
    }

    @Override
    public void move(Long delta) {
        if (animation) {
            Vector2 gForce = new Vector2(0, this.gravity * delta);
            this.velocity = this.velocity.plus(gForce);
            this.position = this.position.plus(this.velocity.times((double) delta / timeUnit));
            if (this.position.y < -1000) {
                this.animation = false;
                this.isActive = false;
            }
        }
    }

    @Override
    public boolean isActive() {
        return this.isActive;
    }
}
