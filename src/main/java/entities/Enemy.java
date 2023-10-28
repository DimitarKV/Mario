package entities;

import enums.CharacterStateEnum;
import enums.Origin;
import interfaces.Collidable;
import types.Vector2;

import java.awt.image.BufferedImage;

public class Enemy extends AbstractCharacter {
    private Camera camera;
    private double deadUpVelocity = 1;

    public Enemy(String spriteRoot, Camera camera, Vector2 position, Origin origin, Integer width, Integer height, Vector2 hitBoxOffset, Vector2 hitBoxDimensions, Collisions collisions) {
        super(spriteRoot, position, origin, null, width, height, hitBoxOffset, hitBoxDimensions, collisions);
        this.camera = camera;
        this.moveSpeed = 0.3;
    }

    @Override
    public void move(Long delta) {
        if (this.isVisible(this.camera)) {
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
    }

    @Override
    protected void calculateSprite(Vector2 newPosition, Vector2 oldPosition) {
        super.calculateSprite(newPosition, oldPosition);
        if (this.dead) {
            this.currentState = CharacterStateEnum.DEAD;
            this.spriteIndex = 0;
        }
    }

    @Override
    public void collidedWith(Collidable other) {
        super.collidedWith(other);
        if (other instanceof Player) {
            if (other.getHitBox().getBottomLeft().y < this.getHitBox().getTopLeft().y + this.getHitBox().dimensions.y / 4) {
                this.dead = true;
                this.setCollidable(false);
                this.mainVelocity = new Vector2(0, -this.deadUpVelocity);
            }
        } else if (other instanceof DieEntity) {
            this.dead = true;
            this.position = new Vector2(this.position.x, 3000);
            this.setCollidable(false);
            this.setSolid(false);
        }
    }

    @Override
    public void xCollision(Collidable other) {
        super.xCollision(other);

        if (this.getTotalVelocity().x > 0) {
            this.stopWalkRight();
            this.walkLeft();
        } else if (this.getTotalVelocity().x < 0) {
            this.stopWalkLeft();
            this.walkRight();
        }
    }

    @Override
    public boolean isActive() {
        return this.position.y < 2000;
    }
}
