package game;

import types.MarioFont;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.SliderUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LevelsMenuPanel extends JPanel {
    private MarioFont mario;
    private JLabel levelTitle;
    private JPanel levelsPanel;
    private JButton level1;
    private JButton level2;
    private JButton level3;
    private int levelNumber;
    private JPanel levelButtonsPanel;
    private JPanel titlePanel;
    private JPanel soundTitlePanel;
    private JLabel soundTitle;
    private JPanel soundPanel;
    private JSlider soundSlider;
    private JPanel soundSliderPanel;
    private JButton mute;
    private JPanel helpPanel;
    private JPanel helpTitlePanel;
    private JLabel helpTitle;
    private JLabel instructions;
    private JPanel continueButtonsPanel;
    private JButton resume;
    private JButton exit;
    private MarioFrame frame;
    private BufferedImage frameBackground;

    public LevelsMenuPanel(MarioFrame frame) throws IOException {
        super.setFocusable(true);
        super.setOpaque(true);
        super.setLayout(new BorderLayout());
        super.setBackground(new Color(0, 0, 0, 200));
        this.frame = frame;

        frameBackground = ImageIO.read(new File("./resources/ui-elements/frame.png"));

        mario = new MarioFont();

        //Level panel
        levelTitle = new JLabel();
        levelTitle.setText("Levels");
        levelTitle.setForeground(new Color(255, 255, 255));
        levelTitle.setBorder(new EmptyBorder(50, 0, 0, 0));
        levelTitle.setFont(mario.deriveFont(60f));
        levelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        levelTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        levelsPanel = new JPanel(new GridLayout(3, 1));
        levelsPanel.setOpaque(false);
        levelsPanel.setBorder(new EmptyBorder(0, 20, 0, 150));

        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBounds(0, 0, levelsPanel.getWidth(), 300);
        titlePanel.add(levelTitle, BorderLayout.CENTER);
        titlePanel.setOpaque(false);

        levelsPanel.add(titlePanel);
        super.add(levelsPanel, BorderLayout.EAST);

        levelButtonsPanel = new JPanel(new GridLayout(3, 1));
        levelButtonsPanel.setOpaque(false);
        levelsPanel.add(levelButtonsPanel);

        ImageIcon buttonIcon = new ImageIcon("./resources/ui-elements/img.png");
        Image buttonImage = buttonIcon.getImage();
        buttonImage = buttonImage.getScaledInstance(350,100, Image.SCALE_SMOOTH);
        buttonIcon = new ImageIcon(buttonImage);

        level1 = new JButton("Level 1");
        level1.setBorder(new EmptyBorder(10, 10, 0, 10));
        level1.setOpaque(false);
        level1.setIcon(buttonIcon);
        level1.setBackground(new Color(0,0,0,0));
        level1.setHorizontalTextPosition(SwingConstants.CENTER);
        level1.setFont(mario.deriveFont(40f));
        level1.setActionCommand("Level1");
        levelButtonsPanel.add(level1);

        level2 = new JButton("Level 2");
        level2.setOpaque(false);
        level2.setBackground(new Color(0,0,0,0));
        level2.setIcon(buttonIcon);
        level2.setBorder(new EmptyBorder(10, 10, 0, 10));
        level2.setHorizontalTextPosition(SwingConstants.CENTER);
        level2.setFont(mario.deriveFont(40f));
        level2.setActionCommand("Level2");
        levelButtonsPanel.add(level2);

        level3 = new JButton("Level 3");
        level3.setOpaque(false);
        level3.setBackground(new Color(0, 0, 0, 0));
        level3.setIcon(buttonIcon);
        level3.setBorder(new EmptyBorder(10, 10, 0, 10));
        level3.setHorizontalTextPosition(SwingConstants.CENTER);
        level3.setFont(mario.deriveFont(40f));
        level3.setActionCommand("Level3");
        levelButtonsPanel.add(level3);

        //Sound panel
        soundTitle = new JLabel();
        soundTitle.setText("Sound");
        soundTitle.setForeground(new Color(255,255,255));
        soundTitle.setBorder(new EmptyBorder(50, 40, 0, 0));
        soundTitle.setFont(mario.deriveFont(60f));
        soundTitle.setHorizontalAlignment(SwingConstants.CENTER);
        soundTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        soundPanel = new JPanel(new GridLayout(3,1));

        soundTitlePanel = new JPanel(new BorderLayout());
        soundTitlePanel.setBounds(0, 0, soundPanel.getWidth(), 300);
        soundTitlePanel.add(soundTitle, BorderLayout.CENTER);
        soundTitlePanel.setOpaque(false);

        soundSlider = new JSlider(-30, 6, -13);
        soundSlider.setBackground(new Color(0, 0, 0, 0));
        soundSlider.setMaximumSize(new Dimension(100, 300));
        soundSlider.setPreferredSize(new Dimension(100, 300));
        soundSlider.setOrientation(JSlider.VERTICAL);

        soundSliderPanel = new JPanel(new BorderLayout());
        soundSliderPanel.setBounds(0, 0, soundPanel.getWidth(), 300);
        soundSliderPanel.setBorder(new EmptyBorder(0, 40, 20, 0));
        soundSliderPanel.add(soundSlider, BorderLayout.CENTER);
        soundSliderPanel.setOpaque(false);

        mute = new JButton("MUTE");
        mute.setOpaque(false);
        mute.setBackground(new Color(0, 0, 0, 0));
        mute.setIcon(buttonIcon);
        mute.setBorder(new EmptyBorder(0, 0, 220, 0));
        mute.setHorizontalTextPosition(SwingConstants.CENTER);
        mute.setFont(mario.deriveFont(40f));
        mute.setActionCommand("Mute");

        soundPanel.setBounds(0, 0, 100, 400);
        soundPanel.setBorder(new EmptyBorder(0, 150, 0, 20));
        soundPanel.add(soundTitlePanel);
        soundPanel.add(soundSliderPanel);
        soundPanel.add(mute);
        soundPanel.setOpaque(false);
        super.add(soundPanel, BorderLayout.WEST);

        //Help panel
        helpTitle = new JLabel();
        helpTitle.setText("Help");
        helpTitle.setForeground(new Color(255, 255, 255));
        helpTitle.setBorder(new EmptyBorder(0, 40, 10, 0));
        helpTitle.setFont(mario.deriveFont(60f));
        helpTitle.setHorizontalAlignment(SwingConstants.CENTER);
        helpTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        helpPanel = new JPanel(new GridLayout(4, 1));

        helpTitlePanel = new JPanel(new BorderLayout());
        helpTitlePanel.setBounds(0, 0, helpPanel.getWidth(), 30);
        helpTitlePanel.add(helpTitle, BorderLayout.CENTER);
        helpTitlePanel.setOpaque(false);

        instructions = new JLabel();
        instructions.setText("Escape --> Menu");
        instructions.setForeground(new Color(255, 255, 255));
        instructions.setBorder(new EmptyBorder(0, 0, 150, 0));
        instructions.setFont(mario.deriveFont(30f));
        instructions.setHorizontalAlignment(SwingConstants.CENTER);
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);

        continueButtonsPanel = new JPanel(new GridLayout(2, 1));
        continueButtonsPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        continueButtonsPanel.setMaximumSize(new Dimension(100, 600));
        continueButtonsPanel.setPreferredSize(new Dimension(100, 500));
        continueButtonsPanel.setOpaque(false);

        resume = new JButton("CONTINUE");
        resume.setOpaque(false);
        resume.setBackground(new Color(0, 0, 0, 0));
        resume.setIcon(buttonIcon);
        resume.setBorder(new EmptyBorder(40, 0, 10, 0));
        resume.setHorizontalTextPosition(SwingConstants.CENTER);
        resume.setFont(mario.deriveFont(40f));
        resume.setActionCommand("Continue");
        continueButtonsPanel.add(resume);

        exit = new JButton("EXIT");
        exit.setOpaque(false);
        exit.setBackground(new Color(0, 0, 0, 0));
        exit.setIcon(buttonIcon);
        exit.setBorder(new EmptyBorder(10, 0, 0, 0));
        exit.setHorizontalTextPosition(SwingConstants.CENTER);
        exit.setFont(mario.deriveFont(40f));
        exit.setActionCommand("Exit");
        continueButtonsPanel.add(exit);

        helpPanel.setBounds(0, 0, 100, 600);
        helpPanel.setBorder(new EmptyBorder(80, 10, 0, 10));
        helpPanel.add(helpTitlePanel);
        helpPanel.add(instructions);
        helpPanel.add(continueButtonsPanel);
        helpPanel.setOpaque(false);
        super.add(helpPanel, BorderLayout.CENTER);

        super.setVisible(true);
    }

    public JButton getLevel1() {
        setLevelNumber(1);
        return level1;
    }

    public JButton getLevel2() {
        setLevelNumber(2);
        return level2;
    }

    public JButton getLevel3() {
        setLevelNumber(3);
        return level3;
    }

    public JButton getResume() {
        return resume;
    }

    public JButton getExit() {
        return exit;
    }

    public JSlider getSoundSlider() {
        return soundSlider;
    }

    public JButton getMute() {
        return mute;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(frameBackground, 0, 0, this.getWidth() - 10, this.getHeight() - 35, null);
    }
}
