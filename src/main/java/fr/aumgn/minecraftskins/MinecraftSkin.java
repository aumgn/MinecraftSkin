package fr.aumgn.minecraftskins;

import static fr.aumgn.minecraftskins.Constants.*;
import static fr.aumgn.minecraftskins.Util.*;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
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
        SkinTransparency parser = new SkinTransparency(skin);
        this.skin = parser.convert();
    }

    public MinecraftSkin(InputStream inputStream) throws IOException {
        this(ImageIO.read(inputStream));
    }

    public MinecraftSkin(File file) throws IOException {
        this(ImageIO.read(file));
    }

    protected Raster getHead(int scale) {
        return rescale(skin.getData(HEAD), scale);
    }

    protected Raster getHelmet(int scale) {
        return rescale(skin.getData(HELMET), scale);
    }

    protected WritableRaster getHeadWithHelmet(int scale) {
        return composeHelmet(getHead(scale), getHelmet(scale));
    }

    protected Raster getBody(int scale) {
        return rescale(skin.getData(BODY), scale);
    }

    protected Raster getLeftArm(int scale) {
        return hflip(getRightArm(scale));
    }

    protected Raster getRightArm(int scale) {
        return rescale(skin.getData(ARM), scale);
    }

    protected Raster getLeftLeg(int scale) {
        return hflip(getRightLeg(scale));
    }

    protected Raster getRightLeg(int scale) {
        return rescale(skin.getData(LEG), scale);
    }

    protected BufferedImage createImage(int width, int height) {
        SampleModel sm = skin.getSampleModel()
                .createCompatibleSampleModel(width, height);
        WritableRaster raster = Raster.createWritableRaster(sm, new Point());
        return new BufferedImage(skin.getColorModel(), raster, false, null);
    }

    public BufferedImage getPreview() {
        return getPreview(1);
    }

    public BufferedImage getPreview(int scale) {
        BufferedImage img = createImage(PREVIEW_WIDTH * scale,
                PREVIEW_HEIGHT * scale);

        img.setData(getHeadWithHelmet(scale)
                .createTranslatedChild( 4 * scale,  0 * scale));
        img.setData(getBody(scale)
                .createTranslatedChild( 4 * scale,  8 * scale));
        img.setData(getLeftArm(scale)
                .createTranslatedChild( 0 * scale,  8 * scale));
        img.setData(getRightArm(scale)
                .createTranslatedChild(12 * scale,  8 * scale));
        img.setData(getLeftLeg(scale)
                .createTranslatedChild( 4 * scale, 20 * scale));
        img.setData(getRightLeg(scale)
                .createTranslatedChild( 8 * scale, 20 * scale));

        return img;
    }

    public BufferedImage getHeadPreview() {
        return getHeadPreview(1);
    }

    public BufferedImage getHeadPreview(int scale) {
        return new BufferedImage(skin.getColorModel(), getHeadWithHelmet(scale),
                false, null);
    }
}
