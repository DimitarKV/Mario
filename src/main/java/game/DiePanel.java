package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import types.MarioFont;

/**
 * A panel for the die state of the game.
 */
public class DiePanel extends JPanel {
    private MarioFont mario;
    private JLabel title;
    private JButton restart;
    private JButton levels;
    private JPanel buttonsPanel;
    private JPanel titlePanel;
    private JLabel filler;
    private MarioFrame frame;
    private ImageIcon buttonIcon;
    private BufferedImage frameBackground;

    /**
     * Constructor for the die state panel.
     */
    public DiePanel(MarioFrame frame) throws IOException {
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
        title.setText("You died!");
        title.setForeground(new Color(255, 255, 255));
        title.setBorder(new EmptyBorder(300, 0, 0, 0));
        title.setFont(mario.deriveFont(100f));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setAlignmentY(Component.TOP_ALIGNMENT);

        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBounds(0, 0, frame.getWidth(), 500);
        titlePanel.add(title, BorderLayout.CENTER);
        titlePanel.setOpaque(false);
        super.add(titlePanel, BorderLayout.NORTH);

        //Button panel
        buttonsPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setMaximumSize(new Dimension(1000, 100));
        buttonsPanel.setPreferredSize(new Dimension(550, 0));
        super.add(buttonsPanel, BorderLayout.CENTER);

        buttonsPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        restart = new JButton("RESTART");
        restart.setBorder(new EmptyBorder(10, 10, 10, 10));
        restart.setOpaque(false);
        restart.setIcon(buttonIcon);
        restart.setBackground(new Color(0, 0, 0, 0));
        restart.setMaximumSize(new Dimension(500, 20));
        restart.setHorizontalTextPosition(SwingConstants.CENTER);
        restart.setFont(mario.deriveFont(40f));
        restart.setPreferredSize(new Dimension(500, 20));
        restart.setActionCommand("Restart");
        buttonsPanel.add(restart);

        levels = new JButton("LEVELS");
        levels.setOpaque(false);
        levels.setBackground(new Color(0, 0, 0, 0));
        levels.setIcon(buttonIcon);
        levels.setBorder(new EmptyBorder(10, 10, 10, 10));
        levels.setHorizontalTextPosition(SwingConstants.CENTER);
        levels.setFont(mario.deriveFont(40f));
        levels.setActionCommand("Levels");
        buttonsPanel.add(levels);

        super.setVisible(true);
    }

    public JButton getRestart() {
        return restart;
    }

    public JButton getLevels() {
        return levels;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(frameBackground, 0, 0, this.getWidth() - 10, this.getHeight() - 35, null);
    }
}
