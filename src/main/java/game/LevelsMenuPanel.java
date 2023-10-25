package game;

import types.MarioFont;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LevelsMenuPanel extends JPanel {
    private MarioFont mario;
    private JLabel title;
    private JButton level1;
    private JButton level2;
    private JButton level3;
    private JPanel buttonsPanel;
    private JPanel titlePanel;
    private JLabel soundTitle;
    private JPanel soundPanel;
    private JSlider soundSlider;
    private MarioFrame frame;
    public LevelsMenuPanel(MarioFrame frame) throws IOException {
        super.setFocusable(true);
        super.setOpaque(true);
        super.setLayout(new GridLayout(5,1));
        super.setBackground(new Color(0,0,0,200));
        this.frame = frame;

        //Font creation
        mario = new MarioFont();

        title = new JLabel();
        title.setText("Levels");
        title.setForeground(new Color(255,255,255));
        title.setBorder(new EmptyBorder(100, 0, 0, 0));
        title.setFont(mario.deriveFont(80f));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBounds(0, 0 , frame.getWidth(), 500);
        titlePanel.add(title, BorderLayout.CENTER);
        titlePanel.setOpaque(false);
        super.add(titlePanel);

        buttonsPanel = new JPanel(new GridLayout(1,3,20,0));
        buttonsPanel.setOpaque(false);
        super.add(buttonsPanel);

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
        buttonsPanel.add(level1);

        level2 = new JButton("Level 2");
        level2.setOpaque(false);
        level2.setBackground(new Color(0,0,0,0));
        level2.setIcon(buttonIcon);
        level2.setBorder(new EmptyBorder(10, 10, 0, 10));
        level2.setHorizontalTextPosition(SwingConstants.CENTER);
        level2.setFont(mario.deriveFont(40f));
        level2.setActionCommand("Soon");
        buttonsPanel.add(level2);

        level3 = new JButton("Level 3");
        level3.setOpaque(false);
        level3.setBackground(new Color(0,0,0,0));
        level3.setIcon(buttonIcon);
        level3.setBorder(new EmptyBorder(10, 10, 0, 10));
        level3.setHorizontalTextPosition(SwingConstants.CENTER);
        level3.setFont(mario.deriveFont(40f));
        level3.setActionCommand("Soon");
        buttonsPanel.add(level3);

        soundTitle = new JLabel();
        soundTitle.setText("Sound Settings");
        soundTitle.setForeground(new Color(255,255,255));
        soundTitle.setBorder(new EmptyBorder(20, 0, 0, 0));
        soundTitle.setFont(mario.deriveFont(80f));
        soundTitle.setHorizontalAlignment(SwingConstants.CENTER);
        soundTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        soundSlider = new JSlider(-80,6,-30);
        soundSlider.setBackground(new Color(0,0,0,0));
        soundSlider.setMaximumSize(new Dimension(600,300));
        soundSlider.setPreferredSize(new Dimension(400,200));

        soundPanel = new JPanel(new BorderLayout());
        soundPanel.setBounds(0, 0 , 700, 400);
        soundPanel.add(soundTitle, BorderLayout.NORTH);
        soundPanel.add(soundSlider, BorderLayout.CENTER);
        soundPanel.setOpaque(false);
        super.add(soundPanel);

        super.setVisible(true);
    }

    public JButton getLevel1() {
        return level1;
    }

    public JButton getLevel2() {
        return level2;
    }

    public JButton getLevel3() {
        return level3;
    }

    public JSlider getSoundSlider() {
        return soundSlider;
    }
}
