import javax.swing.*;
import java.awt.*;

public class MarioFrame extends JFrame {
    private static final int FRAME_WIDTH = 1920;
    private static final int FRAME_HEIGHT = 960;

    public MarioFrame(String frameTitle) throws HeadlessException {
        super(frameTitle);
        super.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
