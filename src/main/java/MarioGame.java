import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class MarioGame implements KeyListener {
    private final MarioFrame frame;
    private final MarioPanel panel;

    private Integer playerPositionX = 100, playerPositionY = 100, playerXVelocity = 0, playerYVelocity = 0;

    public MarioGame() {
        frame = new MarioFrame("TU/e Mario");
        panel = new MarioPanel();
        frame.add(panel);
        panel.addKeyListener(this);

        GameObject mario = new GameObject();
        try {
            mario.image = ImageIO.read(new File("./resources/Mario.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


//        mario.height = 64;
//        mario.width = 32;

        panel.AddGameObject(mario);
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
                playerXVelocity = -1;
            }
            case KeyEvent.VK_D -> {
                playerXVelocity = 1;
            }
            case KeyEvent.VK_SPACE -> {
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
