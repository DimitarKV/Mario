import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class StartMenuPanel extends JPanel {
    private Font mario;
    private JButton start;
    private JButton levels;
    private JButton exit;
    public StartMenuPanel(MarioFrame frame){
        super.setFocusable(true);
        super.setVisible(true);
    }

    public void render(Graphics g) {
        try {
            mario = Font.createFont(Font.TRUETYPE_FONT, new File("./resources/fonts/SuperMario256.ttf")).deriveFont(50f);
        } catch (IOException e) {
            System.out.println("No font found");
        } catch (FontFormatException e) {
            System.out.println("Wrong font format");
        }
        g.setFont(mario);
        g.setColor(Color.white);

        g.drawString("TU/e Mario", (MarioFrame.getFrameWidth()) /2, 100);
    }
}
