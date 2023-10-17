package game;

import game.MarioFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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
    private JPanel filler;

    public StartMenuPanel(MarioFrame frame) {
        super.setFocusable(true);
        super.setVisible(true);
        super.setBackground(Color.green);
        super.setLayout(new BorderLayout(30,40));

        try {
            mario = Font.createFont(Font.TRUETYPE_FONT, new File("./resources/fonts/SuperMario256.ttf"));
        } catch (IOException e) {
            System.out.println("No font found");
        } catch (FontFormatException e) {
            System.out.println("Wrong font format");
        }

        title = new JLabel();
        title.setText("TU/e Mario");
        title.setBorder(new EmptyBorder(20, 0, 0, 0));
        title.setFont(mario.deriveFont(80f));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBounds(0, 0 , frame.getWidth(), 500);
        titlePanel.add(title, BorderLayout.CENTER);
        titlePanel.setOpaque(false);
        super.add(titlePanel, BorderLayout.PAGE_START);

        buttonsPanel = new JPanel(new GridLayout(3,1, 10,10));
        buttonsPanel.setOpaque(true);
        buttonsPanel.setMaximumSize(new Dimension(500,100));
        buttonsPanel.setPreferredSize(new Dimension(500,0));
        buttonsPanel.setBackground(Color.red);
        super.add(buttonsPanel, BorderLayout.EAST);





        start = new JButton("START");
        start.setBorder(new EmptyBorder(10, 10, 10, 10));
        start.setIcon(new ImageIcon("./resources/ui-elements/img.png"));
        start.setMaximumSize(new Dimension(500,20));
        start.setHorizontalTextPosition(SwingConstants.CENTER);
        start.setFont(mario.deriveFont(20f));
        start.setPreferredSize(new Dimension(500,20));
        buttonsPanel.add(start);

        levels = new JButton("LEVELS");
        levels.setBorder(new EmptyBorder(10, 10, 10, 10));
        //levels.setIcon(new ImageIcon("./resources/ui-elements/img.png"));
        //levels.setText();
        //levels.setFont(mario.deriveFont(20f));
        //levels.setSize(5,2);
        buttonsPanel.add(levels);

        exit = new JButton("EXIT");
        exit.setBorder(new EmptyBorder(10, 10, 10, 10));
        //exit.setIcon(new ImageIcon("./resources/ui-elements/img.png"));
        //exit.setText();
        //exit.setFont(mario.deriveFont(20f));
        exit.setSize(5,2);
        buttonsPanel.add(exit);
    }
}
