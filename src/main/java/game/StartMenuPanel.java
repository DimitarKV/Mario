package game;

import types.MarioFont;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class StartMenuPanel extends JPanel {
    private MarioFont mario;
    private JLabel title;
    private JButton start;
    private JButton levels;
    private JButton exit;
    private JPanel buttonsPanel;
    private JPanel titlePanel;
    private JLabel filler;
    private JLabel frameImg;
    private JPanel frameTiles;
    private MarioFrame frame;
    private ImageIcon buttonIcon;
    private BufferedImage frameBackground;

    public StartMenuPanel(MarioFrame frame) throws IOException {
        super.setFocusable(true);
        super.setOpaque(true);
        super.setLayout(new BorderLayout(30, 40));
        super.setBackground(new Color(0, 0, 0, 150));
        this.frame = frame;

        frameBackground = ImageIO.read(new File("./resources/ui-elements/frame.png"));

        //Font
        mario = new MarioFont();

        //Images
        buttonIcon = new ImageIcon("./resources/ui-elements/img.png");
        Image buttonImage = buttonIcon.getImage();
        buttonImage = buttonImage.getScaledInstance(350, 100, Image.SCALE_SMOOTH);
        buttonIcon = new ImageIcon(buttonImage);

        //Title panel
        title = new JLabel();
        title.setText("TU/e Yoshi");
        title.setForeground(new Color(255, 255, 255));
        title.setBorder(new EmptyBorder(0, 0, 200, 0));
        title.setFont(mario.deriveFont(100f));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setAlignmentY(Component.TOP_ALIGNMENT);

        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBounds(0, 0, frame.getWidth(), 500);
        titlePanel.add(title, BorderLayout.CENTER);
        titlePanel.setOpaque(false);
        super.add(titlePanel, BorderLayout.CENTER);

        //Button panel
        buttonsPanel = new JPanel(new GridLayout(7, 1, 20, 20));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setMaximumSize(new Dimension(1000, 100));
        buttonsPanel.setPreferredSize(new Dimension(550, 0));
        super.add(buttonsPanel, BorderLayout.EAST);

        filler = new JLabel();
        buttonsPanel.add(filler);
        buttonsPanel.setBorder(new EmptyBorder(200, 0, 0, 200));

        start = new JButton("START");
        start.setBorder(new EmptyBorder(10, 10, 10, 10));
        start.setOpaque(false);
        start.setIcon(buttonIcon);
        start.setBackground(new Color(0, 0, 0, 0));
        start.setMaximumSize(new Dimension(500, 20));
        start.setHorizontalTextPosition(SwingConstants.CENTER);
        start.setFont(mario.deriveFont(40f));
        start.setPreferredSize(new Dimension(500, 20));
        start.setActionCommand("Start");
        buttonsPanel.add(start);

        levels = new JButton("LEVELS");
        levels.setOpaque(false);
        levels.setBackground(new Color(0, 0, 0, 0));
        levels.setIcon(buttonIcon);
        levels.setBorder(new EmptyBorder(10, 10, 10, 10));
        levels.setHorizontalTextPosition(SwingConstants.CENTER);
        levels.setFont(mario.deriveFont(40f));
        levels.setActionCommand("Levels");
        buttonsPanel.add(levels);

        exit = new JButton("EXIT");
        exit.setOpaque(false);
        exit.setBackground(new Color(0, 0, 0, 0));
        exit.setIcon(buttonIcon);
        exit.setBorder(new EmptyBorder(10, 10, 10, 10));
        exit.setHorizontalTextPosition(SwingConstants.CENTER);
        exit.setFont(mario.deriveFont(40f));
        exit.setActionCommand("Exit");
        buttonsPanel.add(exit);

        super.setVisible(true);
    }
    public JButton getStart() {
        return start;
    }

    public JButton getLevels() {
        return levels;
    }

    public JButton getExit() {
        return exit;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(frameBackground, 0 , 0, this.getWidth() - 10, this.getHeight() - 35, null);
    }
}
