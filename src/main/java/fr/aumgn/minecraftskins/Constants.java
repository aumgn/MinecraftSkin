package fr.aumgn.minecraftskins;

import java.awt.Rectangle;
import java.net.MalformedURLException;
import java.net.URL;

public final class Constants {

    public static final Rectangle HEAD   = new Rectangle( 8,  8,  8,  8);
    public static final Rectangle HELMET = new Rectangle(40,  8,  8,  8);
    public static final Rectangle BODY   = new Rectangle(20, 20,  8, 12);
    public static final Rectangle ARM    = new Rectangle(44, 20,  4, 12);
    public static final Rectangle LEG    = new Rectangle( 4, 20,  4, 12);

    public static final int PREVIEW_WIDTH  = 16;
    public static final int PREVIEW_HEIGHT = 32;

    private static final String SKINS_BASE_URL =
            "http://s3.amazonaws.com/MinecraftSkins/";
    private static final String SKINS_FILE_EXT = ".png";

    public static URL getSkinUrl(String username)
            throws MalformedURLException {
        return new URL(SKINS_BASE_URL + username + SKINS_FILE_EXT);
    }

    private Constants() {
    }
}
