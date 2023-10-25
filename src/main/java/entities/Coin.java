package entities;

import enums.Origin;
import interfaces.Collidable;
import interfaces.Updatable;
import types.Vector2;

import java.awt.image.BufferedImage;

public class Coin extends AbstractCollidable implements Updatable {
    private final Integer value, timeUnit = 1000000;
    private boolean animation = false;
    private final double gravity = -0.000000003;
    private Vector2 velocity = new Vector2();
    public Coin(Vector2 position, Origin origin, BufferedImage image, Integer width, Integer height, Vector2 hitBoxOffset, Vector2 hitBoxDimensions, Integer value) {
        super(position, origin, image, width, height, hitBoxOffset, hitBoxDimensions);
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }

    @Override
    public void collidedWith(Collidable other) {
        if(other instanceof Player) {
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
            if(this.position.y < -1000)
                this.animation = false;
        }
    }
}
