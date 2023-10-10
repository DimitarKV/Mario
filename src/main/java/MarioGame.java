import utils.TileSetReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class MarioGame implements KeyListener {
    private final MarioFrame frame;
    private final MarioPanel panel;
    private final Player mario;

    private Integer currentLevel = 1;

    public MarioGame() {
        frame = new MarioFrame("TU/e Mario");
        panel = new MarioPanel();

        //TODO: CHUNKS
        //TODO: FIIIIIIIIIIX
        mario = new Player(null, null, null, null, null, null);

        frame.add(panel);
        panel.addKeyListener(this);

        TileSetReader.readTileset("./resources/levels/default-tileset.tsj");

    }

    public void run() {
        Long prevRender = System.currentTimeMillis();
        while (true) {
            Long elapsed = System.currentTimeMillis() - prevRender;
            prevRender = System.currentTimeMillis();

            panel.repaint();
        }
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
//            case KeyEvent.VK_UP -> {}
//            case KeyEvent.VK_DOWN -> {}
//            case KeyEvent.VK_LEFT -> {}
//            case KeyEvent.VK_RIGHT -> {}
//            case KeyEvent.VK_W -> {}
//            case KeyEvent.VK_S -> {}
            case KeyEvent.VK_A -> {

            }
            case KeyEvent.VK_D -> {
            }
            case KeyEvent.VK_SPACE -> {
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
