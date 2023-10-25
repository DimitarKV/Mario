package types;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MarioFont {
    private Font mario;

    public MarioFont(){
        try {
            mario = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File("./resources/fonts/SuperMario256.ttf"));
        } catch (IOException e) {
            System.out.println("No font found");
        } catch (FontFormatException e) {
            System.out.println("Wrong font format");
        }
    }

    public Font getMario() {
        return mario;
    }


    public Font deriveFont(float v) {
        return mario.deriveFont(v);
    }
}
