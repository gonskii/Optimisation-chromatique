package SAE.nosAlgo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SolutionHistogramme {
    public static void main(String[] args) {
        int nbCouleur = Integer.parseInt(args[0]);

        try {
            BufferedImage image = ImageIO.read(new File("./images/images_etudiants/originale.jpg"));
            BufferedImage copie = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

            HashMap<Color, ArrayList<Pixel>> histograme = new HashMap<>();

            for (int x = 0; x < image.getHeight(); x++) {
                for (int y = 0; y < image.getWidth(); y++) {
                    // on crée un objet pixel qui représente le pixel ou nous sommes présent
                    Pixel pixel = new Pixel(x, y);
                    // on récupère la couleur du pixel
                    Color color = new Color(image.getRGB(pixel.getX(), pixel.getY()));

                    // on ajoute dans l'histogramme la couleur avec le pixel associé:
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

            // on tri l'histogramme par la taille des listes de pixels
            ArrayList<Map.Entry<Color, ArrayList<Pixel>>> list = new ArrayList<>(histograme.entrySet());
            list.sort((a, b) -> b.getValue().size() - a.getValue().size());

            // on sélectionne seulement les 'nbCouleur' couleurs les plus fréquentes
            ArrayList<Color> couleurs = new ArrayList<Color>();
            for (int i = 0; i < nbCouleur; i++) {
                couleurs.add(list.get(i).getKey());
            }


            //on réparcours l'imag pour changer les valeurs:

            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    // on récupére les couleurs rouge, vert , bleue de l'image:
                    int[] couleurRGB = couleurRGB(image.getRGB(i, j));

                    //on récupére la bonne couleur
                    Color color = new Color(couleurRGB[0], couleurRGB[1], couleurRGB[2]);
                    Color bonneColor = null;

                    double minDistance = Double.MAX_VALUE;

                    //on fait un for qui parcour toute les valeurs
                    for (Color couleur : couleurs) {
                        //on crée une distance avec la méthode distance
                        double distance = distance(couleur, color);

                        //on compare avec min distance et on ajoute les bonnes couleurs
                        if (distance < minDistance) {
                            minDistance = distance;
                            bonneColor = couleur;
                        }
                    }
                    copie.setRGB(i, j, bonneColor.getRGB());
                }
            }
            ImageIO.write(copie, "PNG", new File("./images/resultatNouveau.png"));
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }

    }

    public static double distance(Color a, Color b) {
        int[] rgbC1 = couleurRGB(a.getRGB());
        int[] rgbC2 = couleurRGB(b.getRGB());

        return (Math.pow(rgbC1[0] - rgbC2[0], 2) + Math.pow(rgbC1[1] - rgbC2[1], 2) + Math.pow(rgbC1[2] - rgbC2[2], 2));
    }

    public static int[] couleurRGB(int rgb) {
        int blue = rgb & 0xff;
        int green = (rgb & 0xff00) >> 8;
        int red = (rgb & 0xff0000) >> 16;
        return new int[]{red, green, blue};
    }
}
