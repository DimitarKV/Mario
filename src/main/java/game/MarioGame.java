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
    private final DiePanel diePanel;
    private final WinPanel winPanel;
    private MarioPanel gamePanel;
    private JLayeredPane layers;
    private Integer currentLevel = 1;
    private Level level;
    private StateEnum state;
    private Sound sound;

    public void startLevel(int levelNumber) {
        if (this.gamePanel != null) {
            this.layers.remove(this.gamePanel);
        }

        this.gamePanel = new MarioPanel();
        this.level = new Level(levelNumber, "yoshi", new Rectangle(0, 0, this.frame.getWidth(), this.frame.getHeight()));
        this.gamePanel.setCamera(this.level.getCamera());
        this.gamePanel.addEntities(this.level.getEntities());
        this.gamePanel.setOpaque(true);
        this.gamePanel.setBounds(0, 0, this.frame.getWidth(), this.frame.getHeight());
        this.layers.add(this.gamePanel);
    }

    /**
     * Constructor for the win state panel.
     */
    public MarioGame() throws IOException {
        this.frame = new MarioFrame("TU/e Mario");
        this.state = StateEnum.START_MENU;

        this.layers = new JLayeredPane();
        this.layers.setBounds(0, 0, this.frame.getWidth(), this.frame.getHeight());

        startLevel(1);

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
        this.levelsMenuPanel.getExit().addActionListener(this);
        this.levelsMenuPanel.getResume().addActionListener(this);
        this.levelsMenuPanel.getSoundSlider().addChangeListener(this);
        this.levelsMenuPanel.getMute().addActionListener(this);

        this.diePanel = new DiePanel(this.frame);
        this.diePanel.setOpaque(true);
        this.diePanel.setBounds(0, 0, this.frame.getWidth(), this.frame.getHeight());
        this.diePanel.getRestart().addActionListener(this);
        this.diePanel.getLevels().addActionListener(this);

        this.winPanel = new WinPanel(this.frame);
        this.winPanel.setOpaque(true);
        this.winPanel.setBounds(0, 0, this.frame.getWidth(), this.frame.getHeight());
        this.winPanel.getNextLevel().addActionListener(this);
        this.winPanel.getLevels().addActionListener(this);

        this.sound = new Sound();

        this.layers.add(this.startMenuPanel);
        this.layers.add(this.levelsMenuPanel);
        this.layers.add(this.diePanel);
        this.layers.add(this.winPanel);

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
                if(level.isWon()) {
                    this.state = StateEnum.WIN;
                    sound.stop("themeSong");
                    sound.setFile("win");
                    sound.play("win");
                } else if (level.isDead()) {
                    this.state = StateEnum.DIE;
                    sound.stop("themeSong");
                    sound.setFile("die");
                    sound.play("die");
                }
                gamePanel.setCoinCounter(level.getCoinsCount());
                gamePanel.repaint();

            } else if (state == StateEnum.LEVELS) {
                layers.moveToFront(levelsMenuPanel);
                levelsMenuPanel.setVisible(true);
                startMenuPanel.setVisible(false);
                diePanel.setVisible(false);
                winPanel.setVisible(false);
            } else if (state == StateEnum.START_MENU) {
                layers.moveToFront(startMenuPanel);
                startMenuPanel.setVisible(true);
                diePanel.setVisible(false);
                winPanel.setVisible(false);
                levelsMenuPanel.setVisible(false);
            } else if (state == StateEnum.DIE) {
                layers.moveToFront(diePanel);
                diePanel.setVisible(true);
                winPanel.setVisible(false);
                startMenuPanel.setVisible(false);
                levelsMenuPanel.setVisible(false);
            } else if (state == StateEnum.WIN) {
                layers.moveToFront(winPanel);
                winPanel.setVisible(true);
                diePanel.setVisible(false);
                startMenuPanel.setVisible(false);
                levelsMenuPanel.setVisible(false);
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
                case KeyEvent.VK_W, KeyEvent.VK_SPACE -> {
                    this.level.jump();
                }
                case KeyEvent.VK_ESCAPE -> {
                    this.state = StateEnum.LEVELS;
                    sound.stop("themeSong");
                }
                case KeyEvent.VK_0 -> {
                    startLevel(1);
                }
            }
        } else if (this.state == StateEnum.LEVELS) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ESCAPE -> {
                    if (sound.getAudio() == null) {
                        sound.setFile("themeSong");
                        this.state = StateEnum.GAME;
                        sound.play("themeSong");
                    } else {
                        this.state = StateEnum.GAME;
                        sound.play("themeSong");
                    }

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
                    this.level.deJump();
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.state == StateEnum.GAME) {
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
                currentLevel = 1;
                this.startLevel(currentLevel);
                this.setState(StateEnum.GAME);
                sound.setFile("themeSong");
                sound.play("themeSong");
                sound.loop("themeSong");
            } else if (e.getActionCommand().equals("Level2")) {
                currentLevel = 2;
                this.startLevel(currentLevel);
                this.setState(StateEnum.GAME);
                sound.setFile("themeSong");
                sound.play("themeSong");
                sound.loop("themeSong");
            } else if (e.getActionCommand().equals("Level3")) {
                currentLevel = 3;
                this.startLevel(currentLevel);
                this.setState(StateEnum.GAME);
                sound.setFile("themeSong");
                sound.play("themeSong");
                sound.loop("themeSong");
            } else if (e.getActionCommand().equals("Continue")) {
                this.setState(StateEnum.GAME);
                if (sound.getAudio() == null) {
                    sound.setFile("themeSong");
                    sound.play("themeSong");
                } else {
                    sound.play("themeSong");
                }
            } else if (e.getActionCommand().equals("Mute")) {
                sound.setCurrentVolume(-80f);
                sound.getFloatControl().setValue(-80f);
            } else if (e.getActionCommand().equals("Restart")) {
                this.startLevel(currentLevel);
                this.setState(StateEnum.GAME);
                sound.setFile("themeSong");
                sound.play("themeSong");
                sound.loop("themeSong");
            } else if (e.getActionCommand().equals("NextLevel")) {
                this.currentLevel++;
                if(this.currentLevel > 3)
                    this.currentLevel = 1;
                this.startLevel(this.currentLevel);
                this.setState(StateEnum.GAME);
                sound.setFile("themeSong");
                sound.play("themeSong");
                sound.loop("themeSong");
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (this.state == StateEnum.LEVELS) {
            sound.setCurrentVolume(levelsMenuPanel.getSoundSlider().getValue());
            sound.getFloatControl().setValue(levelsMenuPanel.getSoundSlider().getValue());
        } else {
            return;
        }
    }
}
