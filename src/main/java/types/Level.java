package types;

import entities.*;
import enums.Origin;
import interfaces.Updatable;
import utils.MapReader;
import utils.TileSetReader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private final MapDescriptor map;
    private final List<BufferedImage> tileset;
    private final List<AbstractEntity> entities;
    private final Collisions collisions;
    private final Player player;
    private final Camera camera;

    private final List<Updatable> updatables;

    public Level(int level, String playerName, Rectangle cameraPos) {
        File mapFile = new File("./resources/levels/" + level + "/map.tmj");
        this.map = MapReader.readMap(mapFile);

        String tilesetPath = mapFile.getParent() + "\\" + this.map.tileSets.get(0).source;
        this.tileset = TileSetReader.readTileset(tilesetPath);

        this.entities = new ArrayList<>();
        this.collisions = new Collisions();
        this.updatables = new ArrayList<>();

        Integer pX = 400, pY = 100;
        var startLayer = map.mapLayers.stream().filter(ml -> ml.name.equals("Start")).findFirst().orElse(null);
        if(startLayer != null) {
            pX = startLayer.objects.get(0).x;
            pY = startLayer.objects.get(0).y;
        }

        this.player = new Player("./resources/players/" + playerName, new Vector2(pX, pY), Origin.BOTTOM_LEFT, new Vector2(4, 4), new Vector2(52, 60), 64, 64, this.collisions);
        this.player.setLayer(1);
        this.collisions.addMovingCollider(this.player);
        this.entities.add(player);

        this.camera = new Camera(cameraPos.x, cameraPos.y, cameraPos.width, cameraPos.height);
        this.camera.lockX(this.player, 128 + 32);
        this.camera.updatePosition();



        var tileLayers = map.mapLayers.stream().filter(ml -> ml.type.equals("tilelayer")).toList();
        if (tileLayers != null) {
            for (var tileLayer :
                    tileLayers) {
                for (int row = 0; row < tileLayer.height; row++) {
                    for (int col = 0; col < tileLayer.width; col++) {
                        int tileIndex = tileLayer.data.get(row * tileLayer.width + col);
                        if (tileIndex == 0)
                            continue;

                        tileIndex--;

                        Vector2 position = new Vector2(col * map.tileWidth, row * map.tileHeight);

                        BasicEntity entity = new BasicEntity(position, tileset.get(tileIndex), map.tileWidth, map.tileHeight);
                        entity.setLayer(0);
                        this.entities.add(entity);
                    }
                }
            }
        }

        var objectLayers = map.mapLayers.stream().filter(ml -> ml.type.equals("objectgroup")).toList();
        for (var objectLayer : objectLayers) {
            if(objectLayer.name.equals("Money")) {
                for (var collidableEntity : objectLayer.objects) {
                    BufferedImage imageOptional = null;
                    if(collidableEntity.GId != null && collidableEntity.GId != 0)
                        imageOptional = tileset.get(collidableEntity.GId - 1);

                    Origin origin = Origin.TOP_LEFT;
                    if(collidableEntity.GId != null)
                        origin = Origin.BOTTOM_LEFT;

                    Coin coin = new Coin(
                            new Vector2(collidableEntity.x, collidableEntity.y),
                            origin,
                            imageOptional,
                            collidableEntity.width,
                            collidableEntity.height,
                            new Vector2(5, 5),
                            new Vector2(collidableEntity.width - 10, collidableEntity.height - 5), 1);
                    coin.setLayer(0);
                    coin.setSolid(false);

                    updatables.add(coin);
                    entities.add(coin);
                    this.collisions.addStationaryCollider(coin);
                }
            }

            else {
                for (var collidableEntity : objectLayer.objects) {
                    BufferedImage imageOptional = null;
                    Origin origin = Origin.TOP_LEFT;
                    if(collidableEntity.GId != null)
                        origin = Origin.BOTTOM_LEFT;

                    if(collidableEntity.GId != null && collidableEntity.GId != 0)
                        imageOptional = tileset.get(collidableEntity.GId - 1);

                    BasicCollisionObject object = new BasicCollisionObject(
                            new Vector2(collidableEntity.x, collidableEntity.y),
                            origin,
                            imageOptional,
                            collidableEntity.width,
                            collidableEntity.height,
                            new Vector2(),
                            new Vector2(collidableEntity.width, collidableEntity.height));

                    entities.add(object);
                    this.collisions.addStationaryCollider(object);
                }
            }
        }

    }

    public List<AbstractEntity> getEntities() {
        return this.entities;
    }

    public Collisions getCollisions() {
        return this.collisions;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public void update(Long delta) {
        this.player.move(delta);
        this.camera.updatePosition();
        for (var updatable : updatables) {
            updatable.move(delta);
        }
    }

    public void walkLeft() {
        this.player.walkLeft();
    }

    public void walkRight() {
        this.player.walkRight();
    }

    public void jump() {
        this.player.queueJump();
    }

    public void deJump() {
        this.player.dequeueJump();
    }

    public void stopWalkLeft() {
        this.player.stopWalkLeft();
    }

    public void stopWalkRight() {
        this.player.stopWalkRight();
    }
}
