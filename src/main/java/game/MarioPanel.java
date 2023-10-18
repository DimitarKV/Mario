package game;

import entities.AbstractEntity;
import entities.Camera;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarioPanel extends JPanel {

    private final Map<Integer, List<AbstractEntity>> entities;
    private final Camera camera;


    public MarioPanel(Camera camera) {
        this.entities = new HashMap<>();
        super.setFocusable(true);
        super.setVisible(true);
        super.requestFocusInWindow();

        this.camera = camera;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (var layer : entities.keySet()) {
            for (var entity : entities.get(layer)) {
                if(entity.isVisible(camera))
                    g2d.drawImage(entity.getImage(), (int)(entity.getPosition().x - camera.x), (int)(entity.getPosition().y - camera.y), entity.getWidth(), entity.getHeight(), null);
            }
        }
    }

    public void addEntity(AbstractEntity entity, int layer) {
        if (!entities.containsKey(layer)) {
           entities.put(layer, new ArrayList<>());
           entities.get(layer).add(entity);
           return;
        }
        entities.get(layer).add(entity);
    }
}
