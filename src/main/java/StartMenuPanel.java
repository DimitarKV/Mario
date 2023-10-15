import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class StartMenuPanel extends JPanel {
    private Font mario;
    private JLabel title;
    private JButton start;
    private JButton levels;
    private JButton exit;
    private JLabel buttonsPanel;

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

        buttonsPanel = new JLabel();
        buttonsPanel.setOpaque(true);
        buttonsPanel.setSize(500, 400);
        buttonsPanel.setLayout(new SpringLayout());
        buttonsPanel.setLocation(960, 350);
        buttonsPanel.setBackground(Color.orange);
        frame.add(buttonsPanel);

        title = new JLabel();
        title.setText("TU/e Mario");
        title.setFont(mario.deriveFont(50f));
        super.add(title, BorderLayout.PAGE_START);

        start = new JButton();
        start.setIcon(new ImageIcon("./resources/ui-elements/img.png"));
        start.setMaximumSize(new Dimension(5,2));
        start.setText("START");
        start.setFont(mario.deriveFont(20f));
        start.setSize(5,2);
        buttonsPanel.add(start);

        /*levels = new JButton();
        levels.setIcon(new ImageIcon("./resources/ui-elements/img.png"));
        levels.setText("LEVELS");
        levels.setFont(mario.deriveFont(20f));
        levels.setSize(5,2);
        buttonsPanel.add(levels);

        exit = new JButton();
        exit.setIcon(new ImageIcon("./resources/ui-elements/img.png"));
        exit.setText("EXIT");
        exit.setFont(mario.deriveFont(20f));
        exit.setSize(5,2);
        buttonsPanel.add(exit);*/
    }
}
