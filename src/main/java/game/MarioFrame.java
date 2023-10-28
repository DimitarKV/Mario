package game;

import java.awt.*;
import javax.swing.*;

/**
 * Game frame.
 */
public class MarioFrame extends JFrame {
    private static final int FRAME_WIDTH = 1920;
    private static final int FRAME_HEIGHT = 960;

    /**
     * Constructor for the game frame.
     */
    public MarioFrame(String frameTitle) throws HeadlessException {
        super(frameTitle);
        super.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setResizable(false);
    }

}
