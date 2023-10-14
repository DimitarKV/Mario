import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MarioPanel extends JPanel {

    private final List<BufferedImage> tileset;
    private final List<Integer> mapIndeces;
    private final List<Player> players;
//    private final List<>


    public MarioPanel() {
        this.tileset = new ArrayList<>();
        this.mapIndeces = new ArrayList<>();
        this.players = new ArrayList<>();
        super.setFocusable(true);
        super.setVisible(true);
        super.requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

//        g2d.drawRect(50, 430, 517, 136);

        for (var player :
                players) {
            g2d.drawImage(player.getCurrentSprite(), (int)player.getPosition().x, (int)player.getPosition().y, player.getWidth(), player.getHeight(), null);
        }
    }

//    public void AddGameObject(GameObject object) {
//        gameObjects.add(object);
//        object.index = gameObjects.size() - 1;
//    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }
}
