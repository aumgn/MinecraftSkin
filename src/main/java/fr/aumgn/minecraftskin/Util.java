package fr.aumgn.minecraftskin;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Util {

    private static final int WORKAROUND_ALPHA_X = 32;
    private static final int WORKAROUND_ALPHA_Y = 0;

    private static final int HELMET_X1 = 40;
    private static final int HELMET_Y1 =  8;
    private static final int HELMET_X2 = 48;
    private static final int HELMET_Y2 = 16;

    public static void correctHelmetTransparency(BufferedImage img) {
        WritableRaster raster = img.getRaster();

        for (int i = HELMET_X1; i < HELMET_X2; i++) {
            for (int j = HELMET_Y1; j < HELMET_Y2; j++) {
                if (raster.getSample(i, j, 3) < 0xff) {
                    return;
                }
            }
        }

        int[] workaroundAlpha = raster.getPixel(
                WORKAROUND_ALPHA_X, WORKAROUND_ALPHA_Y, (int[]) null);
        int[] pixel = new int[raster.getNumBands()];
        for (int i = HELMET_X1; i < HELMET_X2; i++) {
            pixel: for (int j = HELMET_Y1; j < HELMET_Y2; j++) {
                raster.getPixel(i, j, pixel);
                for (int k = 0; k < pixel.length; k++) {
                    if (pixel[k] != workaroundAlpha[k]) {
                        continue pixel;
                    }
                }
                raster.setSample(i, j, 3, 0x00);
            }
        }
    }
}
