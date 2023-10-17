package entities;

import enums.CollisionSide;
import interfaces.Collidable;
import types.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Collisions {
    private List<Collidable> moving;
    private List<Collidable> stationary;

    public Collisions() {
        this.moving = new ArrayList<>();
        this.stationary = new ArrayList<>();
    }
    public Collisions(List<Collidable> moving, List<Collidable> stationary) {
        this.moving = moving;
        this.stationary = stationary;
    }

    public void addMovingCollider(Collidable collider) {
        this.moving.add(collider);
    }

    public void addStationaryCollider(Collidable collidable) {
        this.stationary.add(collidable);
    }

//    public void checkCollisions() {
//        for (var movingCollider : this.moving) {
//
//            for (var otherMovingCollider : this.moving) {
//                if (movingCollider == otherMovingCollider)
//                    continue;
//
//                CollisionSide side = movingCollider.collidesWith(otherMovingCollider);
//                if(side != CollisionSide.NONE){
//                    if(side == CollisionSide.RIGHT) {
//                        movingCollider.collidedWith(otherMovingCollider, side, otherMovingCollider.getHitBox().getTopLeft());
//                    } else if (side == CollisionSide.DOWN) {
//                        movingCollider.collidedWith(otherMovingCollider, side, otherMovingCollider.getHitBox().getTopLeft());
//                    } else if (side == CollisionSide.LEFT) {
//                        movingCollider.collidedWith(otherMovingCollider, side, otherMovingCollider.getHitBox().getBottomRight());
//                    } else if (side == CollisionSide.UP) {
//                        movingCollider.collidedWith(otherMovingCollider, side, otherMovingCollider.getHitBox().getBottomRight());
//                    }
//                }
//            }
//
//            for (var stationaryCollider: this.stationary) {
//                CollisionSide side = movingCollider.collidesWith(stationaryCollider);
//
//                if(side != CollisionSide.NONE){
//                    if(side == CollisionSide.RIGHT) {
//                        movingCollider.collidedWith(stationaryCollider, side, stationaryCollider.getHitBox().getTopLeft());
//                    } else if (side == CollisionSide.DOWN) {
//                        movingCollider.collidedWith(stationaryCollider, side, stationaryCollider.getHitBox().getTopLeft());
//                    } else if (side == CollisionSide.LEFT) {
//                        movingCollider.collidedWith(stationaryCollider, side, stationaryCollider.getHitBox().getBottomRight());
//                    } else if (side == CollisionSide.UP) {
//                        movingCollider.collidedWith(stationaryCollider, side, stationaryCollider.getHitBox().getBottomRight());
//                    }
//                }
//            }
//        }
//    }

    public Collidable checkCollisions(Collidable collidable) {
        for (var other :
                this.moving) {
            if(collidable == other)
                continue;
            if(collidable.collidesWith(other))
                return other;
        }

        for (var other :
                this.stationary) {
            if(collidable.collidesWith(other))
                return other;
        }

        return null;
    }

   // public Vector2 collidesWith() {

   // }
}
