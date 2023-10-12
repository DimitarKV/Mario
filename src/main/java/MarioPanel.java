import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MarioPanel extends JPanel {
    private final List<GameObject> gameObjects;
    private final List<Player> players;
//    private final List<>


    public MarioPanel() {
        this.gameObjects = new ArrayList<>();
        this.players = new ArrayList<>();
        super.setFocusable(true);
        super.setVisible(true);
        super.setLayout(null);
        super.requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (var player :
                players) {
            g2d.drawImage(player.getCurrentSprite(), (int)player.getPosition().x, (int)player.getPosition().y, null);
        }
    }

    public void AddGameObject(GameObject object) {
        gameObjects.add(object);
        object.index = gameObjects.size() - 1;
    }

    public void AddPlayer(Player player) {
        this.players.add(player);
    }
}
