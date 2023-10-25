package game;

import entities.AbstractEntity;
import entities.Camera;
import enums.Origin;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class MarioPanel extends JPanel {

    private final List<AbstractEntity> entities;
    private final Camera camera;


    public MarioPanel(Camera camera) {
        this.entities = new ArrayList<>();
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
}
