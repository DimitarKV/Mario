import javax.swing.*;
import java.awt.*;

public class MarioFrame extends JFrame {
    private static final int FRAME_WIDTH = 1280;
    private static final int FRAME_HEIGHT = 720;

    public MarioFrame(String frameTitle) throws HeadlessException {
        super(frameTitle);
        super.setVisible(true);
        super.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
