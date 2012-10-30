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

    protected Raster getHead() {
        return skin.getData(HEAD);
    }

    protected Raster getHelmet() {
        Raster helmet = skin.getData(HELMET);
        return helmet;
    }

    protected WritableRaster getHeadWithHelmet() {
        return composeHelmet(getHead(), getHelmet());
    }

    protected Raster getBody() {
        return skin.getData(BODY);
    }

    protected Raster getLeftArm() {
        return hflip(getRightArm());
    }

    protected Raster getRightArm() {
        return skin.getData(ARM);
    }

    protected Raster getLeftLeg() {
        return hflip(getRightLeg());
    }

    protected Raster getRightLeg() {
        return skin.getData(LEG);
    }

    protected BufferedImage createImage(int width, int height) {
        SampleModel sm = skin.getSampleModel()
                .createCompatibleSampleModel(width, height);
        WritableRaster raster = Raster.createWritableRaster(sm, new Point());
        return new BufferedImage(skin.getColorModel(), raster, false, null);
    }

    public BufferedImage getPreview() {
        BufferedImage img = createImage(PREVIEW_WIDTH, PREVIEW_HEIGHT);

        img.setData(getHeadWithHelmet().createTranslatedChild( 4,  0));
        img.setData(          getBody().createTranslatedChild( 4,  8));
        img.setData(       getLeftArm().createTranslatedChild( 0,  8));
        img.setData(      getRightArm().createTranslatedChild(12,  8));
        img.setData(       getLeftLeg().createTranslatedChild( 4, 20));
        img.setData(      getRightLeg().createTranslatedChild( 8, 20));

        return img;
    }

    public BufferedImage getHeadPreview() {
        return new BufferedImage(skin.getColorModel(), getHeadWithHelmet(),
                false, null);
    }
}
