package types;

import entities.*;
import enums.Origin;
import interfaces.Updatable;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import utils.MapReader;
import utils.TileSetReader;

/**
 * Responsible for level loading and resource management of a specified level.
 */
public class Level {
    private final MapDescriptor map;
    private final List<BufferedImage> tileset;
    private final List<AbstractEntity> entities;
    private final Collisions collisions;
    private final Player player;
    private final Camera camera;
    private Integer coinsCount = 0;
    private final List<Updatable> updatables;
    private boolean won = false;
    private boolean dead = false;
    private Integer pX = 400;
    private Integer pY = 100;

    /**
     * A full constructor.
     *
     * @param level      number of the needed to be loaded level.
     * @param playerName the name of the player to be used as a main character
     * @param cameraPos  the initial position of the camera.
     */
    public Level(int level, String playerName, Rectangle cameraPos) {
        File mapFile = new File("./resources/levels/" + level + "/map.tmj");
        this.map = MapReader.readMap(mapFile);

        String tilesetPath = mapFile.getParent() + "/" + this.map.tileSets.get(0).source;
        this.tileset = TileSetReader.readTileset(tilesetPath);

        this.entities = new ArrayList<>();
        this.collisions = new Collisions();
        this.updatables = new ArrayList<>();

        var startLayer = map.mapLayers.stream()
                .filter(ml -> ml.name.equals("Start")).findFirst().orElse(null);
        if (startLayer != null) {
            pX = startLayer.objects.get(0).x;
            pY = startLayer.objects.get(0).y;
        }

        this.player = new Player(
                "./resources/characters/" + playerName,
                new Vector2(pX, pY),
                Origin.BOTTOM_LEFT,
                new Vector2(4, 4),
                new Vector2(52, 60),
                64,
                64,
                this.collisions);
        this.player.setLayer(1);
        this.collisions.addMovingCollider(this.player);
        this.entities.add(player);

        this.camera = new Camera(cameraPos.x, cameraPos.y, cameraPos.width, cameraPos.height);
        this.camera.lockX(this.player, 128 + 32);
        this.camera.updatePosition();


        var tileLayers = map.mapLayers.stream().filter(ml -> ml.type.equals("tilelayer")).toList();
        for (var tileLayer :
                tileLayers) {
            for (int row = 0; row < tileLayer.height; row++) {
                for (int col = 0; col < tileLayer.width; col++) {
                    int tileIndex = tileLayer.data.get(row * tileLayer.width + col);
                    if (tileIndex == 0) {
                        continue;
                    }

                    tileIndex--;

                    Vector2 position = new Vector2(col * map.tileWidth, row * map.tileHeight);

                    BasicEntity entity = new BasicEntity(
                            position, tileset.get(tileIndex), map.tileWidth, map.tileHeight);

                    entity.setLayer(0);
                    this.entities.add(entity);
                }
            }
        }

        var objectLayers =
                map.mapLayers.stream().filter(ml -> ml.type.equals("objectgroup")).toList();
        for (var objectLayer : objectLayers) {
            for (var collidableEntity : objectLayer.objects) {
                BufferedImage imageOptional = null;
                if (collidableEntity.gid != null && collidableEntity.gid != 0) {
                    imageOptional = tileset.get(collidableEntity.gid - 1);
                }

                Origin origin = Origin.TOP_LEFT;
                if (collidableEntity.gid != null) {
                    origin = Origin.BOTTOM_LEFT;
                }

                if (objectLayer.name.equals("Money")) {
                    Coin coin = new Coin(
                            new Vector2(collidableEntity.x, collidableEntity.y),
                            origin,
                            imageOptional,
                            collidableEntity.width,
                            collidableEntity.height,
                            new Vector2(5, 5),
                            new Vector2(collidableEntity.width - 10, collidableEntity.height - 5),
                            1);
                    coin.setLayer(0);
                    coin.setSolid(false);

                    updatables.add(coin);
                    entities.add(coin);
                    this.collisions.addStationaryCollider(coin);
                } else if (objectLayer.name.equals("Win")) {
                    GameOverEntity gameOverEntity = new GameOverEntity(
                            new Vector2(collidableEntity.x, collidableEntity.y),
                            origin,
                            imageOptional,
                            collidableEntity.width,
                            collidableEntity.height,
                            new Vector2(),
                            new Vector2(collidableEntity.width, collidableEntity.height));

                    entities.add(gameOverEntity);
                    this.collisions.addStationaryCollider(gameOverEntity);
                } else if (objectLayer.name.equals("Die")) {
                    DieEntity dieEntity = new DieEntity(
                            new Vector2(collidableEntity.x, collidableEntity.y),
                            origin,
                            imageOptional,
                            collidableEntity.width,
                            collidableEntity.height,
                            new Vector2(),
                            new Vector2(collidableEntity.width, collidableEntity.height)
                    );

                    entities.add(dieEntity);
                    this.collisions.addStationaryCollider(dieEntity);
                } else if (objectLayer.name.equals("Enemy")) {
                    Enemy enemy = new Enemy(
                            "./resources/characters/enemy",
                            camera,
                            new Vector2(collidableEntity.x, collidableEntity.y),
                            Origin.BOTTOM_LEFT,
                            64,
                            64,
                            new Vector2(),
                            new Vector2(64, 64),
                            collisions);
                    enemy.walkLeft();
                    updatables.add(enemy);
                    entities.add(enemy);
                    this.collisions.addMovingCollider(enemy);
                } else {
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

    /**
     * The main frame calculation method.
     *
     * @param delta parameter representing the time between the current and the last frame.
     */
    public void update(Long delta) {
        if (this.player.isWon()) {
            this.won = true;
        } else if (this.player.isDead()) {
            this.dead = true;
        }
        this.player.move(delta);
        this.camera.updatePosition();

        for (int i = 0; i < updatables.size(); i++) {
            var updatable = updatables.get(i);
            if (!updatable.isActive()) {
                updatables.remove(updatable);
                entities.remove((AbstractEntity) updatable);
            }
        }
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

    public Integer getCoinsCount() {
        return this.player.getCoinsCount();
    }

    public boolean isWon() {
        return this.won;
    }

    public boolean isDead() {
        return this.dead;
    }
}
