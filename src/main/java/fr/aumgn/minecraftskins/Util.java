package fr.aumgn.minecraftskins;

import java.awt.Point;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;

public class Util {

    public static Raster hflip(Raster src) {
        WritableRaster dest = Raster.createWritableRaster(
                src.getSampleModel(), new Point(src.getMinX(), src.getMinY()));

        int x      = src.getMinX();
        int y      = src.getMinY();
        int width  = src.getWidth();
        int height = src.getHeight();
        int maxX   = src.getMinX() + width - 1;

        int[] buf = new int[height * src.getSampleModel().getNumBands()];
        for (int i = 0; i < width; i++) {
            src.getPixels(x + i, y, 1, height, buf);
            dest.setPixels(maxX - i, y, 1, height, buf);
        }

        return dest;
    }

    public static Raster rescale(Raster src, int scale) {
        if (scale == 1) {
            return src;
        }

        int x = src.getMinX();
        int y = src.getMinY();
        int width  = src.getWidth();
        int height = src.getHeight();
        SampleModel sm = src.getSampleModel().createCompatibleSampleModel(
                width * scale, height * scale);
        WritableRaster dest = Raster.createWritableRaster(sm, new Point());

        int[] buf = new int[src.getNumBands()];
        int di = 0;
        for (int i = 0; i < width; i++) {
            int dj = 0;
            for (int j = 0; j < height; j++) {
                src.getPixel(x + i, y + j, buf);

                for (int k = 0; k < scale; k++) {
                    for (int l = 0; l < scale; l++) {
                        dest.setPixel(di + k, dj + l, buf);
                    }
                }

                dj += scale;
            }
            di += scale;
        }

        return dest;
    }

    public static WritableRaster composeHelmet(Raster head, Raster helmet) {
        WritableRaster dest = Raster.createWritableRaster(
                helmet.getSampleModel(), new Point());

        int xHead   = head.getMinX();
        int yHead   = head.getMinY();
        int xHelmet = helmet.getMinX();
        int yHelmet = helmet.getMinY();
        int width   = head.getWidth();
        int height  = head.getHeight();

        int[] buf = new int[head.getNumBands()];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                helmet.getPixel(xHelmet + i, yHelmet + j, buf);
                if (buf[3] == 0x00) {
                    head.getPixel(xHead + i, yHead + j, buf);
                }
                dest.setPixel(i, j, buf);
            }
        }

        return dest;
    }
}
