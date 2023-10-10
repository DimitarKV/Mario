import exceptions.CouldNotReadFileException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameEngine extends JPanel {
    private final JPanel panel;
    private final List<BufferedImage> elements;

    public GameEngine(JPanel panel) {
        this.panel = panel;
        this.elements = new ArrayList<>();
    }

    public int loadElement(String path) {
        try {
            elements.add(ImageIO.read(new File(path)));
            return elements.size() - 1;
        } catch (IOException e) {
            throw new CouldNotReadFileException(e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(elements.get())
    }

    public void Render(int element, int x, int y, int r) {
//        panel.paintComponents(elements.get(element), x, y );
    }
}
