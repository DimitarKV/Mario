package game;

import entities.*;
import enums.PlayerStateEnum;
import enums.StateEnum;
import types.HitBox;
import types.MapDescriptor;
import types.Vector2;
import utils.MapReader;
import utils.TileSetReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MarioGame implements KeyListener, ActionListener {
    private final MarioFrame frame;
    private final StartMenuPanel startMenuPanel;
    private final MarioPanel gamePanel;
    private final Player mario;
    private final MapDescriptor mapDescriptor;
    private JLayeredPane layers;

    private Camera camera;
    private Collisions collisions;
    private List<Tile> tiles;

    private Integer currentLevel = 1;
    private StateEnum state;
    private JLabel xLabel;
    private JLabel yLabel;
    private List<BufferedImage> tileset;

    private boolean displayHitBoxes = true;

    public MarioGame() throws IOException {

        tiles = new ArrayList<>();

        File map = new File("./resources/levels/1/map.tmj");
        mapDescriptor = MapReader.readMap(map);
        String path = map.getParent() + "\\" + mapDescriptor.tileSets.get(0).source;
        tileset = TileSetReader.readTileset(path);


        var tileLayer = mapDescriptor.mapLayers.stream().filter(ml -> ml.type.equals("tilelayer")).findFirst().orElse(null);
        if (tileLayer != null) {
            for (int row = 0; row < tileLayer.height; row++) {
                for (int col = 0; col < tileLayer.width; col++) {
                    int tileIndex = tileLayer.data.get(row * tileLayer.width + col);
                    if (tileIndex == 0)
                        continue;

                    tileIndex--;

                    Vector2 position = new Vector2(col * mapDescriptor.tileWidth, row * mapDescriptor.tileHeight);

                    Tile tile = new Tile(position, tileset.get(tileIndex), mapDescriptor.tileWidth, mapDescriptor.tileHeight);
                    tiles.add(tile);
                }
            }
        }
        Integer marioX = 400, marioY = 100;
        var startLayer = mapDescriptor.mapLayers.stream().filter(ml -> ml.name.equals("Start")).findFirst().orElse(null);
        if(startLayer != null) {
            marioX = startLayer.objects.get(0).x;
            marioY = startLayer.objects.get(0).y;
        }


        frame = new MarioFrame("TU/e Mario");
        camera = new Camera(0, 0, frame.getWidth(), frame.getHeight());
        collisions = new Collisions();
        mario = new Player("./resources/players/yoshi", new Vector2(marioX, marioY),new Vector2(4, 4), new Vector2(52, 60), 64, 64, collisions);
        camera.lockX(mario, 128 + 32);
        camera.updatePosition();

        gamePanel = new MarioPanel(camera);

        BufferedImage barrier = displayHitBoxes ? ImageIO.read(new File("./resources/barrier.png")) : null;

        var objectLayers = mapDescriptor.mapLayers.stream().filter(ml -> ml.type.equals("objectgroup")).toList();
        for (var objectLayer : objectLayers) {
            for (var collidableEntity : objectLayer.objects) {
                BasicCollisionObject object = new BasicCollisionObject(
                        new Vector2(collidableEntity.x, collidableEntity.y),
                        barrier,
                        collidableEntity.width,
                        collidableEntity.height,
                        new Vector2(),
                        new Vector2(collidableEntity.width, collidableEntity.height));

                gamePanel.addEntity(object, 3);
                collisions.addStationaryCollider(object);
            }
        }


        collisions.addMovingCollider(mario);


        frame.setLayout(null);
        frame.addKeyListener(this);



        layers = new JLayeredPane();
        layers.setBounds(0, 0, this.frame.getWidth(), this.frame.getHeight());

        startMenuPanel = new StartMenuPanel(frame);
        startMenuPanel.setOpaque(true);
        startMenuPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        startMenuPanel.getStart().addActionListener(this);
        startMenuPanel.getLevels().addActionListener(this);
        startMenuPanel.getExit().addActionListener(this);


        gamePanel.setOpaque(true);
        gamePanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        gamePanel.addEntity(mario, 2);

        for (var tile : tiles) {
            gamePanel.addEntity(tile, 1);
        }

        xLabel = new JLabel();
        yLabel = new JLabel();
        xLabel.setVisible(true);
        yLabel.setVisible(true);
        gamePanel.add(xLabel);
        gamePanel.add(yLabel);

        layers.add(gamePanel);
        layers.add(startMenuPanel);

        frame.add(layers);
        frame.setVisible(true);
    }

    public void run() {
        Long prevRender = System.nanoTime();
        state = StateEnum.MENU;
        Long prevFPSReading = 0L, currentFrames = 0L, lastFPSRender = 0L;

        while (true) {
            Long elapsed = System.nanoTime() - prevRender;
            prevRender = System.nanoTime();


            if (state == StateEnum.GAME) {

                //TODO: CHECK FOR COLLISIONS
//                    this.collisions.checkCollisions();

                layers.moveToFront(gamePanel);
                mario.move(elapsed);

//                    if (System.nanoTime() - lastFPSRender > 500000000) {
//                        xLabel.setText("" + currentFrames * 2);
//                        currentFrames = 0L;
//                        lastFPSRender = System.nanoTime();
//                    }
//                    currentFrames++;

                xLabel.setText("" + mario.getHitBox().getBottomLeft().x);
                yLabel.setText("" + mario.getHitBox().getBottomLeft().y);

                camera.updatePosition();
                gamePanel.repaint();
            } else if (state == StateEnum.MENU) {
                layers.moveToFront(startMenuPanel);
                startMenuPanel.setVisible(true);
            }
            frame.requestFocusInWindow();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (state == StateEnum.GAME) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A -> {
                    mario.walkLeft();
                }
                case KeyEvent.VK_D -> {
                    mario.walkRight();
                }
                case KeyEvent.VK_W -> {
                    mario.jump();
                }
                case KeyEvent.VK_SPACE -> {
                    mario.jump();
                }
                case KeyEvent.VK_ESCAPE -> {
                    this.state = StateEnum.MENU;
                }
            }
        } else if (this.state == StateEnum.MENU) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ESCAPE -> {
                    this.state = StateEnum.GAME;
                }
            }
        }
    }

    public void setState(StateEnum state) {
        this.state = state;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (state == StateEnum.GAME) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A -> {
                    mario.stopWalkLeft();
                }
                case KeyEvent.VK_D -> {
                    mario.stopWalkRight();
                }
                case KeyEvent.VK_SPACE -> {
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start")){
            this.setState(StateEnum.GAME);
        } else if (e.getActionCommand().equals("Levels")){

        } else if (e.getActionCommand().equals("Exit")){
            System.exit(0);
        }
    }
}
