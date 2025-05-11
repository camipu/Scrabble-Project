package edu.upc.prop.clusterxx.presentacio;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {
    private static Font customFont;

    static {
        try (InputStream is = FontLoader.class.getResourceAsStream("/fonts/VT323-Regular.ttf")) {
            if (is != null) {
                customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(18f);
            } else {
                System.err.println("Font no trobada!");
                customFont = new Font("Serif", Font.PLAIN, 18); // Font per defecte
            }
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            customFont = new Font("Serif", Font.PLAIN, 18); // Font per defecte
        }
    }

    public static Font getCustomFont(float size) {
        return customFont.deriveFont(size);
    }

    public static Font getCustomFont() {
        return customFont;
    }
}
