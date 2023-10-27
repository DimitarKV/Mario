package game;

import entities.AbstractEntity;
import entities.Camera;
import enums.Origin;
import types.MarioFont;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import java.util.List;

public class MarioPanel extends JPanel {

    private final List<AbstractEntity> entities;
    private Camera camera;
    private JLabel coins;
    private int coinCounter;
    private MarioFont mario;


    public MarioPanel() {
        this.entities = new ArrayList<>();
        super.setLayout(new BorderLayout());

        mario = new MarioFont();

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

    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        coins.setText("Coins: " + coinCounter);

        Graphics2D g2d = (Graphics2D) g;

        entities.sort(Comparator.comparing(AbstractEntity::getLayer));

        for (var entity : entities) {
            if (entity.isVisible(camera)) {
                int xPos = (int) (entity.getPosition().x - camera.x);
                int yPos = (int) (entity.getPosition().y - camera.y);

                g2d.drawImage(entity.getImage(), xPos, yPos, entity.getWidth(), entity.getHeight(), null);
            }
        }
    }

    public void addEntity(AbstractEntity entity) {
        entities.add(entity);
    }

    public void addEntities(List<AbstractEntity> allEntities) {
        entities.addAll(allEntities);
    }

    public void setCoinCounter(Integer coinCounter) {
        this.coinCounter = coinCounter;
    }
}
