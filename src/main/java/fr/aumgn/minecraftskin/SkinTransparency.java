package fr.aumgn.minecraftskin;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class SkinTransparency {

    private final ColorModel colorModel;
    private final WritableRaster raster;

    public SkinTransparency(BufferedImage img) {
        colorModel = img.getColorModel();
        raster = img.copyData(null);
    }

    public BufferedImage convert() {
        setOpaque(0,  0, 32, 16);
        setOpaque(0, 16, 64, 32);
        setTransparent(32, 0, 64, 16);

        return new BufferedImage(colorModel, raster, false, null);
    }

    private boolean hasTransparency(int x1, int y1, int x2, int y2) {
        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                if (raster.getSample(i, j, 3) < 0xff) {
                    return true;
                }
            }
        }

        return false;
    }

    private void setTransparent(int x1, int y1, int x2, int y2) {
        if (hasTransparency(x1, y1, x2, y2)) {
            return;
        }

        int[] workaroundAlpha = raster.getPixel(x1, y1, (int[]) null);
        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                int[] pixel = raster.getPixel(i, j, (int[]) null);
                boolean isWorkaroundAlpha = true;
                for (int k = 0; k < 3; k++) {
                    if (pixel[k] != workaroundAlpha[k]) {
                        isWorkaroundAlpha = false;
                        break;
                    }
                }
                if (isWorkaroundAlpha) {
                    raster.setSample(i, j, 3, 0x00);
                }
            }
        }
    }

    private void setOpaque(int x1, int y1, int x2, int y2) {
        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                raster.setSample(i, j, 3, 0xff);
            }
        }
    }
}
