import enums.PlayerStateEnum;
import enums.StateEnum;
import types.HitBox;
import types.MapDescriptor;
import types.Vector2;
import utils.MapReader;
import utils.TileSetReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MarioGame implements KeyListener {
    private final MarioFrame frame;
    private final StartMenuPanel startMenuPanel;
    private final MarioPanel gamePanel;
    private final Player mario, fakeMario;
    private final MapDescriptor mapDescriptor;
    private JLayeredPane layers;

    private Camera camera;
    private Collisions collisions;

    private Integer currentLevel = 1;
    private StateEnum state;
    private JLabel xLabel;
    private JLabel yLabel;
    private List<BufferedImage> tileset;

    public MarioGame() {
        Map<PlayerStateEnum, List<BufferedImage>> sprites = new HashMap<>();
        try {
            sprites.put(PlayerStateEnum.MOVING_LEFT, Arrays.asList(ImageIO.read(new File("./resources/mario/mario_yoshi/ML0.png")), ImageIO.read(new File("./resources/mario/mario_yoshi/ML1.png")), ImageIO.read(new File("./resources/mario/mario_yoshi/ML0.png"))));
            sprites.put(PlayerStateEnum.MOVING_RIGHT, Arrays.asList(ImageIO.read(new File("./resources/mario/mario_yoshi/MR0.png")), ImageIO.read(new File("./resources/mario/mario_yoshi/MR1.png")), ImageIO.read(new File("./resources/mario/mario_yoshi/MR0.png"))));
            sprites.put(PlayerStateEnum.AIRBORNE_LEFT, Arrays.asList(ImageIO.read(new File("./resources/mario/mario_yoshi/J.png"))));
            sprites.put(PlayerStateEnum.AIRBORNE_RIGHT, Arrays.asList(ImageIO.read(new File("./resources/mario/mario_yoshi/J.png"))));
            sprites.put(PlayerStateEnum.STATIONARY_LEFT, Arrays.asList(ImageIO.read(new File("./resources/mario/mario_yoshi/ML0.png"))));
            sprites.put(PlayerStateEnum.STATIONARY_RIGHT, Arrays.asList(ImageIO.read(new File("./resources/mario/mario_yoshi/MR0.png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        tileset = TileSetReader.readTileset("./resources/levels/level1/default-tileset.tsj");
        mapDescriptor = MapReader.readMap("./resources/levels/level1/map1.tmj");


        collisions = new Collisions();
        mario = new Player(sprites, new Vector2(100, 100), 67, 71, collisions);
        fakeMario = new Player(sprites, new Vector2(300, 100), 120, 120, collisions);
        collisions.addMovingCollider(mario);
        collisions.addMovingCollider(fakeMario);


        frame = new MarioFrame("TU/e Mario");
        frame.setLayout(null);
        frame.addKeyListener(this);

        camera = new Camera(0, 0, frame.getWidth(), frame.getHeight());
        camera.lock(mario, frame.getWidth() / 4, frame.getHeight() / 2);

        layers = new JLayeredPane();
        layers.setBounds(0, 0, this.frame.getWidth(), this.frame.getHeight());

        startMenuPanel = new StartMenuPanel(frame);
        startMenuPanel.setOpaque(true);
        startMenuPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        gamePanel = new MarioPanel(camera);
        gamePanel.setOpaque(true);
        gamePanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());


        gamePanel.addEntity(mario);
        gamePanel.addEntity(fakeMario);
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
                    fakeMario.move(elapsed);

//                    if (System.nanoTime() - lastFPSRender > 500000000) {
//                        xLabel.setText("" + currentFrames * 2);
//                        currentFrames = 0L;
//                        lastFPSRender = System.nanoTime();
//                    }
//                    currentFrames++;

                    xLabel.setText("" + mario.getPosition().x);

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
                case KeyEvent.VK_RIGHT -> {
                    this.camera.setLocation(this.camera.x + 1, this.camera.y);
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
}
