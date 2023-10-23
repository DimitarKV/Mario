package game;

import enums.StateEnum;
import types.Level;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
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
    private FloatControl floatControl;
    private AudioInputStream themeSong;
    private AudioInputStream jumpSound;
    private Clip clip;

    public MarioGame() throws IOException {
        this.frame = new MarioFrame("TU/e Mario");
        this.state = StateEnum.START_MENU;
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

        this.levelsMenuPanel = new LevelsMenuPanel(this.frame);
        this.levelsMenuPanel.setOpaque(true);
        this.levelsMenuPanel.setBounds(0, 0, this.frame.getWidth(), this.frame.getHeight());
        this.levelsMenuPanel.getLevel1().addActionListener(this);
        this.levelsMenuPanel.getLevel2().addActionListener(this);
        this.levelsMenuPanel.getLevel3().addActionListener(this);
        this.levelsMenuPanel.getSoundSlider().addChangeListener(this);

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
                    try{
                        this.jumpSound = AudioSystem.getAudioInputStream(new File("./resources/sounds/jump.wav"));
                        clip = AudioSystem.getClip();
                        clip.open(jumpSound);
                        clip.start();
                    }catch(Exception ex){
                        System.out.println("No sound found");
                    }
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
                case KeyEvent.VK_SPACE -> {
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start")) {
            this.setState(StateEnum.GAME);
            try{
                this.themeSong = AudioSystem.getAudioInputStream(new File("./resources/sounds/themeSong.wav"));
                //floatControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
                clip = AudioSystem.getClip();
                clip.open(themeSong);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }catch(Exception ex){
                System.out.println("No sound found");
            }
        } else if (e.getActionCommand().equals("Levels")) {
            this.setState(StateEnum.LEVELS);
        } else if (e.getActionCommand().equals("Exit")) {
            System.exit(0);
        } else if (e.getActionCommand().equals("Level1")) {
            this.setState(StateEnum.GAME);
            try{
                this.themeSong = AudioSystem.getAudioInputStream(new File("./resources/sounds/themeSong.wav"));
                clip = AudioSystem.getClip();
                clip.open(themeSong);
                clip.start();
            }catch(Exception ex){
                System.out.println("No sound found");
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        //floatControl.setValue(levelsMenuPanel.getSoundSlider().getValue());
    }
}
