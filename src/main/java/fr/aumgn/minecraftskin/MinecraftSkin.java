package fr.aumgn.minecraftskin;

import static fr.aumgn.minecraftskin.Constants.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public class MinecraftSkin {

    public static MinecraftSkin fetch(String username) throws IOException {
        return fromUrl(Constants.getSkinUrl(username));
    }

    public static MinecraftSkin fromUrl(URL url) throws IOException {
        return new MinecraftSkin(url.openStream());
    }

    private final BufferedImage skin;

    public MinecraftSkin(BufferedImage skin) {
        this.skin = skin;
        Util.correctHelmetTransparency(skin);
    }

    public MinecraftSkin(InputStream inputStream) throws IOException {
        this(ImageIO.read(inputStream));
    }

    public MinecraftSkin(File file) throws IOException {
        this(ImageIO.read(file));
    }

    protected void draw(Graphics2D g, int sx, int sy, int sw, int sh, int dx,
            int dy, int scale) {
        draw(g, sx, sy, sw, sh, dx, dy, scale, false, false);
    }

    protected void draw(Graphics2D g, int sx, int sy, int sw, int sh, int dx,
            int dy, int scale, boolean flip) {
        draw(g, sx, sy, sw, sh, dx, dy, scale, flip, false);
    }

    protected void draw(Graphics2D g, int sx, int sy, int sw, int sh, int dx,
            int dy, int scale, boolean flip, boolean transparent) {
        int dx1 = dx * scale;
        int dx2 = (dx + sw)  * scale;
        int dy1 = dy * scale;
        int dy2 = (dy + sh) * scale;

        if (flip) {
            int tmp = dx1;
            dx1 = dx2;
            dx2 = tmp;
        }

        if (transparent) {
            g.drawImage(skin, dx1, dy1, dx2, dy2,
                    sx, sy, sx + sw, sy + sh,
                    null);
        } else {
            g.drawImage(skin, dx1, dy1, dx2, dy2,
                    sx, sy, sx + sw, sy + sh,
                    Color.BLACK, null);
        }
    }

    protected BufferedImage createImage(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public BufferedImage getPreview() {
        return getPreview(1);
    }

    public BufferedImage getPreview(int scale) {
        BufferedImage img = createImage(PREVIEW_WIDTH * scale,
                PREVIEW_HEIGHT * scale);

        Graphics2D g = img.createGraphics();
        draw(g, SRC_HEAD_X, SRC_HEAD_Y, SRC_HEAD_W, SRC_HEAD_H,
                PREVIEW_HEAD_X, PREVIEW_HEAD_Y, scale);
        draw(g, SRC_HELMET_X, SRC_HELMET_Y, SRC_HELMET_W, SRC_HELMET_H,
                PREVIEW_HELMET_X, PREVIEW_HELMET_Y, scale, false, true);
        draw(g, SRC_BODY_X, SRC_BODY_Y, SRC_BODY_W, SRC_BODY_H,
                PREVIEW_BODY_X, PREVIEW_BODY_Y, scale);
        draw(g, SRC_ARM_X, SRC_ARM_Y, SRC_ARM_W, SRC_ARM_H,
                PREVIEW_LEFT_ARM_X, PREVIEW_LEFT_ARM_Y, scale);
        draw(g, SRC_ARM_X, SRC_ARM_Y, SRC_ARM_W, SRC_ARM_H,
                PREVIEW_RIGHT_ARM_X, PREVIEW_RIGHT_ARM_Y, scale, true);
        draw(g, SRC_LEG_X, SRC_LEG_Y, SRC_LEG_W, SRC_LEG_H,
                PREVIEW_LEFT_LEG_X, PREVIEW_LEFT_LEG_Y, scale);
        draw(g, SRC_LEG_X, SRC_LEG_Y, SRC_LEG_W, SRC_LEG_H,
                PREVIEW_RIGHT_LEG_X, PREVIEW_RIGHT_LEG_Y, scale, true);

        g.dispose();
        return img;
    }

    public BufferedImage getHeadPreview() {
        return getHeadPreview(1);
    }

    public BufferedImage getHeadPreview(int scale) {
        BufferedImage img = createImage(HEAD_WIDTH * scale,
                HEAD_HEIGHT * scale);

        Graphics2D g = img.createGraphics();
        draw(g, SRC_HEAD_X, SRC_HEAD_Y, SRC_HEAD_W, SRC_HEAD_H,
                HEAD_HEAD_X, HEAD_HEAD_Y, scale);
        draw(g, SRC_HELMET_X, SRC_HELMET_Y, SRC_HELMET_W, SRC_HELMET_H,
                HEAD_HELMET_X, HEAD_HELMET_Y, scale, false, true);

        g.dispose();
        return img;
    }
}
