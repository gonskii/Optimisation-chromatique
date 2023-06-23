package SAE.algorithme;

import SAE.Solution;

import java.awt.*;
import java.awt.image.BufferedImage;


import static SAE.utilitaire.Outils.evaluer;
import static SAE.utilitaire.Outils.retournerRGB;

public class SolutionSpectrale implements Solution {
    /**
     * Méthode qui permet de résoudre le problème
     * @param nombreDeCouleurs, nombre de couleurs souhaite
     * @param bfImage, le buffer de l'image
     * @return , retourne le buffer de l'image finale avec le nombre de couleurs souhaite
     */
    @Override
    public  BufferedImage resoudre(int nombreDeCouleurs, BufferedImage bfImage) {
        // min et max : int des couleurs situées au extrémité de la plage de couleur de l'image.
        int maxR = -1, maxG = -1, maxB = -1, minR = -1, minG = -1, minB = -1;

        // un tableau de couleurs à remplir afin d'avoir les couleurs prédéfinies
        Color[] couleurs = new Color[nombreDeCouleurs];

        //on parcours une premiere fois l image afin de connaitre la plage de couleurs
        BufferedImage retour = new BufferedImage(bfImage.getWidth(), bfImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < bfImage.getHeight(); i++) {
            for (int j = 0; j < bfImage.getWidth(); j++) {
                // on recupère la couleur aux coordonnées précises
                int[] couleurRGB = retournerRGB(bfImage.getRGB(j, i));
                // on initialise nos variables avec la première couleur
                if (i == 0 && j == 0) {
                    minR = couleurRGB[0];
                    maxR = couleurRGB[0];
                    minG = couleurRGB[1];
                    maxG = couleurRGB[1];
                    minB = couleurRGB[2];
                    maxB = couleurRGB[2];
                }
                // sinon on la compare avec le min et max
                else {
                    // on compare R
                    if (minR > couleurRGB[0]) {
                        minR = couleurRGB[0];
                    } else if (maxR < couleurRGB[0]) {
                        maxR = couleurRGB[0];
                    }
                    // on compare G
                    if (minG > couleurRGB[1])
                        minG = couleurRGB[1];
                    else if (maxG < couleurRGB[1])
                        maxG = couleurRGB[1];
                    // on compare B
                    if (minB > couleurRGB[2])
                        minB = couleurRGB[2];
                    else if (maxB < couleurRGB[2])
                        maxB = couleurRGB[2];
                }

            }
        }

        // on calcul la plage
        int[] plage = new int[]{maxR - minR, maxG - minG, maxB - minB};

        // l'écart de chaque portion pour R, G et B
        int[] portions = new int[]{plage[0] / nombreDeCouleurs, plage[1] / nombreDeCouleurs, plage[2] / nombreDeCouleurs};

        // on remplie le tableau de couleurs
        for (int i = 0; i < nombreDeCouleurs; i++) {
            int r = minR + i * portions[0];
            int g = minG + i * portions[1];
            int b = minB + i * portions[2];
            couleurs[i] = new Color(r, g, b);
        }

        /*
        for (Color c : couleurs) {
            System.out.println(c);
        }
         */

        // descente de gradient
        //main Q5
        for (int i = 0; i < bfImage.getHeight(); i++) {
            for (int j = 0; j < bfImage.getWidth(); j++) {

                Color gardee = null;
                int tmp = -1;

                for (Color c : couleurs) {
                    int eval = evaluer(new Color(bfImage.getRGB(j, i)), c);
                    if (eval < tmp) {
                        tmp = eval;
                        gardee = c;
                    } else if (tmp == -1) {
                        tmp = eval;
                        gardee = c;
                    }

                }

                retour.setRGB(j, i, gardee.getRGB());

            }
        }
        return retour;
    }
}
