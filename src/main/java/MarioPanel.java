import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MarioPanel extends JPanel {
    private final List<GameObject> gameObjects;
//    private final List<>


    public MarioPanel() {
        this.gameObjects = new ArrayList<>();
        super.setFocusable(true);
        super.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        GameObject mario = new GameObject();
        try {
            mario.image = ImageIO.read(new File("./resources/Mario.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        float ratio = (float)mario.image.getWidth() / mario.image.getHeight();
        g2d.drawImage(mario.image.getScaledInstance(32, 32 * (int)(1/ratio),Image.SCALE_DEFAULT), 0, 0, null);
    }

    public void AddGameObject(GameObject object) {
        gameObjects.add(object);
        object.index = gameObjects.size() - 1;
    }
}
