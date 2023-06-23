package SAE.nosAlgo;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Outils {

    public static int evaluer(Color c1, Color c2){
        int[] tabC1 = retournerRGB(c1.getRGB());
        int[] tabC2 = retournerRGB(c2.getRGB());

        int d=0;
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

    public static Random instance = null;

    public static Random getRandom() {
        if (instance == null) {
            instance = new Random(1234567890);
        }
        return instance;
    }

    public static double distance(Color a, Color b)
    {
        int[] rgbC1 = retournerRGB(a.getRGB());
        int[] rgbC2 = retournerRGB(b.getRGB());

        return (Math.pow(rgbC1[0]-rgbC2[0],2)+Math.pow(rgbC1[1]-rgbC2[1],2)+Math.pow(rgbC1[2]-rgbC2[2],2));
    }

    public static void addHashmap(HashMap<Color, ArrayList<Pixel>> histograme, Pixel pixel, Color color) {
        if (histograme.containsKey(color)) {
            // on récupère la liste des pixels associée à la couleur
            ArrayList<Pixel> pixels = histograme.get(color);
            // on ajoute le pixel à la liste des pixels
            pixels.add(pixel);
        } else {
            // on crée une nouvelle liste de pixels et on ajoute le pixel actuel
            ArrayList<Pixel> pixels = new ArrayList<Pixel>();
            pixels.add(pixel);
            // on ajoute la couleur avec la liste des pixels dans l'histogramme
            histograme.put(color, pixels);
        }
    }
}
