package game;

import enums.StateEnum;
import types.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class MarioGame implements KeyListener, ActionListener {
    private final MarioFrame frame;
    private final StartMenuPanel startMenuPanel;
    private final MarioPanel gamePanel;
    private JLayeredPane layers;
    private Integer currentLevel = 1;
    private Level level;
    private StateEnum state;

    public MarioGame() throws IOException {
        this.frame = new MarioFrame("TU/e Mario");
        this.state = StateEnum.MENU;
        this.level = new Level(currentLevel, new Rectangle(0, 0, this.frame.getWidth(), this.frame.getHeight()));

        this.gamePanel = new MarioPanel(this.level.getCamera());
        this.gamePanel.addEntities(this.level.getEntities());

        this.layers = new JLayeredPane();
        this.layers.setBounds(0, 0, this.frame.getWidth(), this.frame.getHeight());

        this.startMenuPanel = new StartMenuPanel(this.frame);
        this.startMenuPanel.setOpaque(true);
        this.startMenuPanel.setBounds(0, 0, this.frame.getWidth(), this.frame.getHeight());
        this.startMenuPanel.getStart().addActionListener(this);
        this.startMenuPanel.getLevels().addActionListener(this);
        this.startMenuPanel.getExit().addActionListener(this);

        this.gamePanel.setOpaque(true);
        this.gamePanel.setBounds(0, 0, this.frame.getWidth(), this.frame.getHeight());

        this.layers.add(this.gamePanel);
        this.layers.add(this.startMenuPanel);

        this.frame.setLayout(null);
        this.frame.addKeyListener(this);
        this.frame.add(this.layers);
        this.frame.setVisible(true);
    }

    public void run() {
        Long prevRender = System.nanoTime();

        while (true) {
            Long delta = System.nanoTime() - prevRender;
            prevRender = System.nanoTime();

            if (state == StateEnum.GAME) {
                layers.moveToFront(gamePanel);
                level.update(delta);
                gamePanel.repaint();
            } else if (state == StateEnum.MENU) {
                layers.moveToFront(startMenuPanel);
                startMenuPanel.setVisible(true);
            }
            frame.requestFocusInWindow();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (state == StateEnum.GAME) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A -> {
                    this.level.walkLeft();
                }
                case KeyEvent.VK_D -> {
                    this.level.walkRight();
                }
                case KeyEvent.VK_W -> {
                    this.level.jump();
                }
                case KeyEvent.VK_SPACE -> {
                    this.level.jump();
                }
                case KeyEvent.VK_ESCAPE -> {
                    this.state = StateEnum.MENU;
                }
            }
        } else if (this.state == StateEnum.MENU) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ESCAPE -> {
                    this.state = StateEnum.GAME;
                }
            }
        }
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (state == StateEnum.GAME) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A -> {
                    this.level.stopWalkLeft();
                }
                case KeyEvent.VK_D -> {
                    this.level.stopWalkRight();
                }
                case KeyEvent.VK_SPACE -> {
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start")) {
            this.setState(StateEnum.GAME);
        } else if (e.getActionCommand().equals("Levels")) {

        } else if (e.getActionCommand().equals("Exit")) {
            System.exit(0);
        }
    }
}
