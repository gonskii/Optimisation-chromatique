package TP;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Distance {

    public static long distance(BufferedImage img1, BufferedImage img2) {
        long d = 0;

        for (int i = 0; i < img1.getHeight(); i++) {
            for (int j = 0; j < img1.getWidth(); j++) {

                d += evaluer(img1.getRGB(j,i), img2.getRGB(j,i));

            }
        }
        return d;
    }


    public static long evaluer(int rgb1, int rgb2){
        int[] tabC1 = retournerRGB(rgb1);
        int[] tabC2 = retournerRGB(rgb2);

        long d=0;
        for (int i = 0; i < 3; i++) {
            d+=Math.pow((tabC1[i]-tabC2[i]),2);
        }
        return d;
    }

    public static int[] retournerRGB(int rgb){
        int blue = rgb & 0xff;
        int green = (rgb & 0xff00) >> 8;
        int red = (rgb & 0xff0000) >> 16;
        return new int[]{red,green,blue};
    }

}
