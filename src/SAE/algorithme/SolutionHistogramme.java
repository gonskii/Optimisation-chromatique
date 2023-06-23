package SAE.algorithme;


import SAE.Solution;
import SAE.utilitaire.Outils;
import SAE.utilitaire.Pixel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SolutionHistogramme implements Solution {

    /**
     * Méthode qui permet de résoudre le problème
     * @param nombreDeCouleurs, nombre de couleurs souhaite
     * @param bfImage, le buffer de l'image
     * @return , retourne le buffer de l'image finale avec le nombre de couleurs souhaite
     */
    @Override
    public BufferedImage resoudre(int nombreDeCouleurs, BufferedImage bfImage)
    {
        BufferedImage copie = new BufferedImage(bfImage.getWidth(), bfImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        HashMap<Color, ArrayList<Pixel>> histograme = new HashMap<>();

        for (int x = 0; x < bfImage.getHeight(); x++) {
            for (int y = 0; y < bfImage.getWidth(); y++) {
                // on crée un objet pixel qui représente le pixel ou nous sommes présent
                Pixel pixel = new Pixel(x, y);
                // on récupère la couleur du pixel
                Color color = new Color(bfImage.getRGB(pixel.getX(), pixel.getY()));

                // on ajoute dans l'histogramme la couleur avec le pixel associé:
                Outils.addHashmap(histograme, pixel, color);
            }
        }

        // on tri l'histogramme par la taille des listes de pixels
        ArrayList<Map.Entry<Color, ArrayList<Pixel>>> list = new ArrayList<>(histograme.entrySet());
        list.sort((a, b) -> b.getValue().size() - a.getValue().size());

        // on sélectionne seulement les 'nbCouleur' couleurs les plus fréquentes
        ArrayList<Color> couleurs = new ArrayList<Color>();
        for (int i = 0; i < nombreDeCouleurs; i++) {
            couleurs.add(list.get(i).getKey());
        }


        //on réparcours l'imag pour changer les valeurs:
        for (int i = 0; i < bfImage.getWidth(); i++) {
            for (int j = 0; j < bfImage.getHeight(); j++) {
                // on récupére les couleurs rouge, vert , bleue de l'image:
                int[] couleurRGB = Outils.retournerRGB(bfImage.getRGB(i, j));

                //on récupére la bonne couleur
                Color color = new Color(couleurRGB[0], couleurRGB[1], couleurRGB[2]);
                Color bonneColor = null;

                double minDistance = Double.MAX_VALUE;

                //on fait un for qui parcour toute les valeurs
                for (Color couleur : couleurs) {
                    //on crée une distance avec la méthode distance
                    double distance = Outils.distance(couleur, color);

                    //on compare avec min distance et on ajoute les bonnes couleurs
                    if (distance < minDistance) {
                        minDistance = distance;
                        bonneColor = couleur;
                    }
                }
                copie.setRGB(i, j, bonneColor.getRGB());
            }
        }
        return copie;
    }
}
