package game;

import enums.StateEnum;
import types.Level;
import types.Sound;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.IOException;

public class MarioGame implements KeyListener, ActionListener, ChangeListener {
    private final MarioFrame frame;
    private final StartMenuPanel startMenuPanel;
    private final LevelsMenuPanel levelsMenuPanel;
    private final MarioPanel gamePanel;
    private JLayeredPane layers;
    private Integer currentLevel = 1;
    private Level level;
    private StateEnum state;
    private Sound sound;

    public MarioGame() throws IOException {
        this.frame = new MarioFrame("TU/e Mario");
        this.level = new Level(currentLevel, "yoshi",new Rectangle(0, 0, this.frame.getWidth(), this.frame.getHeight()));
        this.state = StateEnum.START_MENU;

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

        this.levelsMenuPanel = new LevelsMenuPanel(this.frame);
        this.levelsMenuPanel.setOpaque(true);
        this.levelsMenuPanel.setBounds(0, 0, this.frame.getWidth(), this.frame.getHeight());
        this.levelsMenuPanel.getLevel1().addActionListener(this);
        this.levelsMenuPanel.getLevel2().addActionListener(this);
        this.levelsMenuPanel.getLevel3().addActionListener(this);
        this.levelsMenuPanel.getSoundSlider().addChangeListener(this);

        this.sound = new Sound();

        this.gamePanel.setOpaque(true);
        this.gamePanel.setBounds(0, 0, this.frame.getWidth(), this.frame.getHeight());

        this.layers.add(this.gamePanel);
        this.layers.add(this.startMenuPanel);
        this.layers.add(this.levelsMenuPanel);

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
                if(level.isEnd()) {
                    this.state = StateEnum.START_MENU;
                }
                gamePanel.setCoinCounter(level.getCoinsCount());
                gamePanel.repaint();
            } else if (state == StateEnum.LEVELS) {
                layers.moveToFront(levelsMenuPanel);
                levelsMenuPanel.setVisible(true);
                startMenuPanel.setVisible(false);
            } else if(state == StateEnum.START_MENU){
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
                case KeyEvent.VK_A-> {
                    this.level.walkLeft();
                }
                case KeyEvent.VK_D -> {
                    this.level.walkRight();
                }
                case KeyEvent.VK_W, KeyEvent.VK_SPACE -> {
                    this.level.jump();
                }
                case KeyEvent.VK_ESCAPE -> {
                    this.state = StateEnum.LEVELS;
                }
            }
        } else if (this.state == StateEnum.LEVELS) {
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
                case KeyEvent.VK_W -> {
                    this.level.deJump();
                }
                case KeyEvent.VK_SPACE -> {
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.state == StateEnum.GAME){
            return;
        } else {
            if (e.getActionCommand().equals("Start")) {
                this.setState(StateEnum.GAME);
                sound.setFile("themeSong");
                sound.play("themeSong");
                sound.loop("themeSong");
            } else if (e.getActionCommand().equals("Levels")) {
                this.setState(StateEnum.LEVELS);
            } else if (e.getActionCommand().equals("Exit")) {
                System.exit(0);
            } else if (e.getActionCommand().equals("Level1")) {
                this.setState(StateEnum.GAME);
                sound.setFile("themeSong");
                sound.play("themeSong");
                sound.loop("themeSong");
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        sound.setCurrentVolume(levelsMenuPanel.getSoundSlider().getValue());
        sound.getFloatControl().setValue(levelsMenuPanel.getSoundSlider().getValue());
    }
}
