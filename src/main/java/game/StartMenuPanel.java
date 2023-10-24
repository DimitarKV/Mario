package game;

import enums.StateEnum;
import game.MarioFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class StartMenuPanel extends JPanel {
    private Font mario;
    private JLabel title;



    private JButton start;
    private JButton levels;
    private JButton exit;
    private JPanel buttonsPanel;
    private JPanel titlePanel;
    private JLabel filler;
    private JLabel filler1;
    private MarioFrame frame;
    private MarioGame game;
    public StartMenuPanel(MarioFrame frame) throws IOException {
        super.setFocusable(true);
        super.setOpaque(true);
        super.setLayout(new BorderLayout(30,40));
        super.setBackground(new Color(0,0,0,70));
        this.frame = frame;

        try {
            mario = Font.createFont(Font.TRUETYPE_FONT, new File("./resources/fonts/SuperMario256.ttf"));
        } catch (IOException e) {
            System.out.println("No font found");
        } catch (FontFormatException e) {
            System.out.println("Wrong font format");
        }

        title = new JLabel();
        title.setText("TU/e Mario");
        title.setForeground(new Color(255,255,255));
        title.setBorder(new EmptyBorder(100, 0, 0, 0));
        title.setFont(mario.deriveFont(80f));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBounds(0, 0 , frame.getWidth(), 500);
        titlePanel.add(title, BorderLayout.CENTER);
        titlePanel.setOpaque(false);
        super.add(titlePanel, BorderLayout.PAGE_START);

        buttonsPanel = new JPanel(new GridLayout(7,1, 20,20));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setMaximumSize(new Dimension(1000,100));
        buttonsPanel.setPreferredSize(new Dimension(450,0));
        super.add(buttonsPanel, BorderLayout.EAST);


        ImageIcon buttonIcon = new ImageIcon("./resources/ui-elements/img.png");
        Image buttonImage = buttonIcon.getImage();
        buttonImage = buttonImage.getScaledInstance(350,100, Image.SCALE_SMOOTH);
        buttonIcon = new ImageIcon(buttonImage);

        filler = new JLabel();
        filler1 = new JLabel();

        buttonsPanel.add(filler);
        //buttonsPanel.add(filler1);
        buttonsPanel.setBorder(new EmptyBorder(0, 0, 0, 100));



        start = new JButton("START");
        start.setBorder(new EmptyBorder(10, 10, 10, 10));
        start.setOpaque(false);
        start.setIcon(buttonIcon);
        start.setBackground(new Color(0,0,0,0));
        start.setMaximumSize(new Dimension(500,20));
        start.setHorizontalTextPosition(SwingConstants.CENTER);
        start.setFont(mario.deriveFont(40f));
        start.setPreferredSize(new Dimension(500,20));
        start.setActionCommand("Start");
        buttonsPanel.add(start);

        levels = new JButton("LEVELS");
        levels.setOpaque(false);
        levels.setBackground(new Color(0,0,0,0));
        levels.setIcon(buttonIcon);
        levels.setBorder(new EmptyBorder(10, 10, 10, 10));
        levels.setHorizontalTextPosition(SwingConstants.CENTER);
        levels.setFont(mario.deriveFont(40f));
        levels.setActionCommand("Levels");
        buttonsPanel.add(levels);

        exit = new JButton("EXIT");
        exit.setOpaque(false);
        exit.setBackground(new Color(0,0,0,0));
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
}