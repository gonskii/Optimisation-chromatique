package SAE.nosAlgo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Outils {
    /**
     * Attributs:
     */
    public static Random instance = null;

    /**
     * Méthode qui permet d'évaluer et renvoie un int
     * @param c1, couleur une
     * @param c2, couleur deux
     * @return , int représentant la distance
     */
    public static int evaluer(Color c1, Color c2){
        int[] tabC1 = retournerRGB(c1.getRGB());
        int[] tabC2 = retournerRGB(c2.getRGB());

        int d=0;
        for (int i = 0; i < 3; i++) {
            d+=Math.pow((tabC1[i]-tabC2[i]),2);
        }
        return d;
    }

    /**
     * Méthode qui permet de calculer la distance entre deux couleurs en revoyant un double
     * @param a, couleur a
     * @param b, couleur b
     * @return , retourne un double correspondant à la distance
     */
    public static double distance(Color a, Color b)
    {
        int[] rgbC1 = retournerRGB(a.getRGB());
        int[] rgbC2 = retournerRGB(b.getRGB());

        return (Math.pow(rgbC1[0]-rgbC2[0],2)+Math.pow(rgbC1[1]-rgbC2[1],2)+Math.pow(rgbC1[2]-rgbC2[2],2));
    }

    /**
     * Méthode qui permet de renvoyer un tableau correspondant au RGB d'une couleur
     * @param rgb, le int de la couleur,
     * @return , retourne un tableau d'int correspondant au RGB
     */
    public static int[] retournerRGB(int rgb){
        int blue = rgb & 0xff;
        int green = (rgb & 0xff00) >> 8;
        int red = (rgb & 0xff0000) >> 16;
        return new int[]{red,green,blue};
    }

    /**
     * Méthode qui permet de renvoyer l'instance de Random
     * @return , retourne une instance de l'objet Random
     */
    public static Random getRandom() {
        if (instance == null) {
            instance = new Random(1234567890);
        }
        return instance;
    }

    /**
     * Méthode qui permet d'ajouter dans un hashmap
     * @param hashMap, le hashmap
     * @param pixel, le pixel
     * @param color, la couleur
     */
    public static void addHashmap(HashMap<Color, ArrayList<Pixel>> hashMap, Pixel pixel, Color color) {
        if (hashMap.containsKey(color)) {
            // on récupère la liste des pixels associée à la couleur
            ArrayList<Pixel> pixels = hashMap.get(color);
            // on ajoute le pixel à la liste des pixels
            pixels.add(pixel);
        } else {
            // on crée une nouvelle liste de pixels et on ajoute le pixel actuel
            ArrayList<Pixel> pixels = new ArrayList<Pixel>();
            pixels.add(pixel);
            // on ajoute la couleur avec la liste des pixels dans l'histogramme
            hashMap.put(color, pixels);
        }
    }

    /**
     * Méthode qui permet de comparer deux images
     * @param img1, la premiere image
     * @param img2, la deuxieme image
     */
    public static long distance(BufferedImage img1, BufferedImage img2) {
        long d = 0;

        for (int i = 0; i < img1.getHeight(); i++) {
            for (int j = 0; j < img1.getWidth(); j++) {

                d += evaluerLong(img1.getRGB(j,i), img2.getRGB(j,i));

            }
        }
        return d;
    }

    /**
     * Méthode qui permet d'évaluer et renvoie un int
     * @param rgb1, couleur une
     * @param rgb2, couleur deux
     * @return , long représentant la distance
     */
    public static long evaluerLong(int rgb1, int rgb2){
        int[] tabC1 = retournerRGB(rgb1);
        int[] tabC2 = retournerRGB(rgb2);

        long d=0;
        for (int i = 0; i < 3; i++) {
            d+=Math.pow((tabC1[i]-tabC2[i]),2);
        }
        return d;
    }
}
