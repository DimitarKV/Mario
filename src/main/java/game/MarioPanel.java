package game;

import entities.AbstractEntity;
import entities.Camera;
import enums.Origin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarioPanel extends JPanel {

    private final Map<Integer, List<AbstractEntity>> entities;
    private final Camera camera;
    private JLabel coins;
    private int coinCounter;
    private Font mario;


    public MarioPanel(Camera camera) {
        this.entities = new HashMap<>();

        super.setLayout(new BorderLayout());

        try {
            mario = Font.createFont(Font.TRUETYPE_FONT, new File("./resources/fonts/SuperMario256.ttf"));
        } catch (IOException e) {
            System.out.println("No font found");
        } catch (FontFormatException e) {
            System.out.println("Wrong font format");
        }

        coins = new JLabel();
        coins.setText("Coins: " + coinCounter);
        coins.setForeground(new Color(255,255,255));
        coins.setBorder(new EmptyBorder(20, 20, 0, 0));
        coins.setFont(mario.deriveFont(40f));
        super.add(coins, BorderLayout.PAGE_START);

        super.setFocusable(true);
        super.setBackground(new Color(137, 218, 250));
        super.setVisible(true);
        super.requestFocusInWindow();

        this.camera = camera;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (var layer : entities.keySet().stream().sorted().toList()) {
            for (var entity : entities.get(layer)) {
                if(entity.isVisible(camera)){
                    int xPos = (int)(entity.getPosition().x - camera.x);
                    int yPos = (int)(entity.getPosition().y - camera.y);

                    g2d.drawImage(entity.getImage(), xPos, yPos, entity.getWidth(), entity.getHeight(), null);
                }
            }
        }
    }

    public void addEntity(AbstractEntity entity) {
        if (!entities.containsKey(entity.getLayer())) {
           entities.put(entity.getLayer(), new ArrayList<>());
           entities.get(entity.getLayer()).add(entity);
           return;
        }
        entities.get(entity.getLayer()).add(entity);
    }

    public void addEntities(List<AbstractEntity> allEntities) {
        for (var entity :
                allEntities) {
            if (!entities.containsKey(entity.getLayer())) {
                entities.put(entity.getLayer(), new ArrayList<>());
                entities.get(entity.getLayer()).add(entity);
            }
            entities.get(entity.getLayer()).add(entity);
        }
    }
}
