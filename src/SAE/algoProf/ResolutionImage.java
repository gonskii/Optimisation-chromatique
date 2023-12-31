package SAE.algoProf;

import SAE.Solution;
import SAE.utilitaire.Pixel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static SAE.utilitaire.Outils.*;

public class ResolutionImage implements  Solution{

    public BufferedImage resoudre(int nombreDeCouleurs, BufferedImage bfImage){
        // un tableau de couleurs à remplir
        Color[] couleurs = new Color[nombreDeCouleurs];

            // image de retour
            BufferedImage retour = new BufferedImage(bfImage.getWidth(), bfImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

            HashMap<Color, ArrayList<Pixel>> groupe;

            Random random = getRandom();

            // initialisation des centroïdes
            for (int i = 0; i < nombreDeCouleurs; i++) {
                // choisir un pixel au hasard
                int x = random.nextInt(bfImage.getWidth());
                int y = random.nextInt(bfImage.getHeight());
                couleurs[i] = new Color(bfImage.getRGB(x, y));
            }

            boolean fini = false;

            // boucle principale
            while (!fini) {

                // Initialisation des groupes
                groupe = new HashMap<Color, ArrayList<Pixel>>();
                for (int i = 0; i < nombreDeCouleurs; i++) {
                    groupe.put(couleurs[i], new ArrayList<Pixel>());
                }

                // Construction des groupes
                for (int x = 0; x < bfImage.getHeight(); x++) {
                    for (int y = 0; y < bfImage.getWidth(); y++) {
                        //on crée un objet pixel qui représente le pixel ou nous sommes présent
                        Pixel pixel = new Pixel(x, y);

                        //récupérer la couleur du pixel
                        int[] couleurRGB = retournerRGB(bfImage.getRGB(pixel.getX(), pixel.getY()));

                        //on récupére la bonne couleur
                        Color color = new Color(couleurRGB[0], couleurRGB[1], couleurRGB[2]);
                        Color bonneColor = null;

                        double minDistance = Double.MAX_VALUE;

                        //Comparer a cette couleur a tout les couleurs qu'on a dans notre tableau pour l'ajoute a notre histogramme
                        for (Color couleur : couleurs) {
                            //on crée une distance avec la méthode distance
                            double distance = evaluer(couleur, color);

                            //on compare avec min distance et on ajoute les bonnes couleurs
                            if (distance < minDistance) {
                                minDistance = distance;
                                bonneColor = couleur;
                            }
                        }
                        // Récupérez la liste des pixels associée à la couleur
                        ArrayList<Pixel> pixels = groupe.get(bonneColor);
                        // Ajoutez le pixel à la liste des pixels
                        pixels.add(pixel);
                    }
                }

                // Mise à jour de centroïdes
                for (int i = 0; i < nombreDeCouleurs; i++) {
                    Color centroïde = couleurs[i];
                    int[] sommeRGB = new int[3];
                    for (Pixel p : groupe.get(centroïde)) {
                        int[] retourRGB = retournerRGB(bfImage.getRGB(p.getX(), p.getY()));
                        for (int j = 0; j < 3; j++) {
                            sommeRGB[j] += retourRGB[j];
                        }
                    }
                    // faire la moyenne
                    for (int j = 0; j < 3; j++) {
                        sommeRGB[j] = sommeRGB[j] / groupe.get(centroïde).size();
                    }
                    // affecter les nouvelles couleurs
                    Color nouvelle = new Color(sommeRGB[0], sommeRGB[1], sommeRGB[2]);
                    int[] centRGB = retournerRGB(centroïde.getRGB());
                    fini = fini || (centRGB[0] == sommeRGB[0] && centRGB[1]==sommeRGB[1] && centRGB[2]==sommeRGB[2]);
                    couleurs[i] = nouvelle;
                }
            }

            for (int i = 0; i < bfImage.getHeight(); i++) {
                for (int j = 0; j < bfImage.getWidth(); j++) {

                    Color gardee = null;
                    int tmp = -1;

                    for (Color c:couleurs ) {
                        int eval = evaluer(new Color(bfImage.getRGB(j,i)), c);
                        if (eval<tmp){
                            tmp=eval;
                            gardee=c;
                        } else if (tmp==-1){
                            tmp=eval;
                            gardee=c;
                        }

                    }

                    retour.setRGB(j, i, gardee.getRGB());

                }
            }

            return retour;

    }

}
