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

    public StartMenuPanel(MarioFrame frame) {
        super.setFocusable(true);
        super.setVisible(true);
        super.setBackground(Color.green);
        frame.setLayout(new GridLayout());

        try {
            mario = Font.createFont(Font.TRUETYPE_FONT, new File("./resources/fonts/SuperMario256.ttf")).deriveFont(50);
        } catch (IOException e) {
            System.out.println("No font found");
        } catch (FontFormatException e) {
            System.out.println("Wrong font format");
        }


        title = new JLabel();
        title.setText("TU/e Mario");
        title.setFont(mario);
        title.setMinimumSize(new Dimension(50,20));
        title.setLocation(frame.getWidth()/2, 10);
        super.add(title);
        title.setVisible(true);

        /*start = new JButton();
        start.setIcon(new ImageIcon("./resources/ui-elements/img.png"));
        start.setText("START");
        start.setFont(mario.deriveFont(20));
        start.setLocation(frame.getWidth()-200, frame.getHeight()/2);
        start.setSize(50,20);
        super.add(start);
        start.setVisible(true);

        levels = new JButton();
        levels.setIcon(new ImageIcon("./resources/ui-elements/img.png"));
        levels.setText("LEVELS");
        levels.setFont(mario.deriveFont(20));
        levels.setLocation(frame.getWidth()-200, frame.getHeight()/2+70);
        levels.setSize(50,20);
        super.add(levels);
*/
        exit = new JButton();
        exit.setIcon(new ImageIcon("./resources/ui-elements/img.png"));
        exit.setText("EXIT");
        exit.setFont(mario);
        exit.setLocation(frame.getWidth()-200, frame.getHeight()/2+140);
        exit.setSize(50,20);
        super.add(exit);



    }
}
