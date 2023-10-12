import enums.StateEnum;
import types.Vector2;
import utils.TileSetReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MarioGame implements KeyListener {
    private final MarioFrame frame;
//    private final StartMenuPanel startMenuPanel;
    private final MarioPanel gamePanel;
    private final Player mario;

    private Integer currentLevel = 1;
    private StateEnum state;

    public MarioGame() {
        frame = new MarioFrame("TU/e Mario");
//        startMenuPanel = new StartMenuPanel(frame);
        gamePanel = new MarioPanel();
        frame.addKeyListener(this);
        frame.add(gamePanel);

        List<BufferedImage> sprites = new ArrayList<>();
        try {
            sprites.add(ImageIO.read(new File("./resources/mario/mario_yoshi/MR0.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mario = new Player(sprites, new Vector2(100, 100), 67, 71);

//        frame.add(startMenuPanel);
        gamePanel.AddPlayer(mario);

        TileSetReader.readTileset("./resources/levels/level1/default-tileset.tsj");

    }

    public void run() {
        Long prevRender = System.nanoTime();
        state = StateEnum.GAME;
        while (true) {
            Long elapsed = System.nanoTime() - prevRender;
            prevRender = System.nanoTime();
            mario.move(elapsed);

            frame.repaint();
        }
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(state == StateEnum.GAME){
            switch (e.getKeyCode()) {
                // case KeyEvent.VK_UP -> {}
                // case KeyEvent.VK_DOWN -> {}
                // case KeyEvent.VK_LEFT -> {}
                // case KeyEvent.VK_RIGHT -> {}
                // case KeyEvent.VK_W -> {}
                // case KeyEvent.VK_S -> {}
                case KeyEvent.VK_A -> {
                    mario.walkLeft();
                }
                case KeyEvent.VK_D -> {
                    mario.walkRight();
                }
                case KeyEvent.VK_SPACE -> {
                    mario.jump();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(state == StateEnum.GAME){
            switch (e.getKeyCode()) {
                // case KeyEvent.VK_UP -> {}
                // case KeyEvent.VK_DOWN -> {}
                // case KeyEvent.VK_LEFT -> {}
                // case KeyEvent.VK_RIGHT -> {}
                // case KeyEvent.VK_W -> {}
                // case KeyEvent.VK_S -> {}
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
