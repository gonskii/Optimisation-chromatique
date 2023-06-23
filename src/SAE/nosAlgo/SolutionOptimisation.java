package SAE.nosAlgo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class SolutionOptimisation implements Solution {

    /**
     * Méthode qui permet de résoudre le problème
     * @param nombreDeCouleurs, nombre de couleurs souhaite
     * @param bfImage, le buffer de l'image
     * @return , retourne le buffer de l'image finale avec le nombre de couleurs souhaite
     */
    @Override
    public  BufferedImage resoudre(int nombreDeCouleurs, BufferedImage bfImage) {
            BufferedImage copie = new BufferedImage(bfImage.getWidth(), bfImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

            HashMap<Color, ArrayList<Pixel>> couleursFinal = new HashMap<>();

            Random random = Outils.getRandom();
            ArrayList<Color> couleurs = new ArrayList<Color>();
            HashSet<Integer> pixelUse = new HashSet<>();
            double minCouleurDistance = 600.0;


            while(couleurs.size()<nombreDeCouleurs)
            {
                // choisir un pixel au hasard
                int x = random.nextInt(bfImage.getWidth());
                int y = random.nextInt(bfImage.getHeight());

                int pixelIndex = y * bfImage.getWidth() +x;

                if(pixelUse.contains(pixelIndex))
                {
                    continue;
                }

                pixelUse.add(pixelIndex);

                int rgb = bfImage.getRGB(x, y);
                Color newColor = new Color(rgb);

                boolean goodDist = true;

                for (Color existingColor : couleurs) {
                    if (Outils.distance(newColor, existingColor) < minCouleurDistance) {
                        goodDist = false;
                        break;
                    }
                }

                if (goodDist) {
                    couleurs.add(newColor);
                }
            }


            // on crée un histograme:
            HashMap<Color, ArrayList<Pixel>> hashMapColor = new HashMap<Color, ArrayList<Pixel>>();
            for (int x = 0; x < bfImage.getHeight(); x++) {
                for (int y = 0; y < bfImage.getWidth(); y++)
                {
                    //on crée un objet pixel qui représente le pixel ou nous sommes présent
                    Pixel pixel = new Pixel(x,y);

                    //récupérer la couleur du pixel
                    int[] couleurRGB = Outils.retournerRGB(bfImage.getRGB(pixel.getX(),pixel.getY()));

                    //on récupére la bonne couleur
                    Color color = new Color(couleurRGB[0],couleurRGB[1], couleurRGB[2]);
                    Color bonneColor = null;

                    double minDistance = Double.MAX_VALUE;

                    //Comparer a cette couleur a tout les couleurs qu'on a dans notre tableau pour l'ajoute a notre histogramme
                    for (Color couleur : couleurs)
                    {
                        //on crée une distance avec la méthode distance
                        double distance = Outils.distance(couleur,color);

                        //on compare avec min distance et on ajoute les bonnes couleurs
                        if(distance<minDistance)
                        {
                            minDistance = distance;
                            bonneColor = couleur;
                        }
                    }

                    //on ajoute dans l'histograme la bonne couleur avec le pixel associé:
                    Outils.addHashmap(hashMapColor, pixel, bonneColor);

                }
            }

            //Tu parcours tout l'histograme :
            for (Map.Entry<Color, ArrayList<Pixel>> entry : hashMapColor.entrySet()) {
                Color couleur = entry.getKey();
                ArrayList<Pixel> pixels = entry.getValue();

                //Bonne couleur (pixels)
                Color bonneColor = null;
                //distance min
                double minDistance = Double.MAX_VALUE;
                for(Pixel pixel: pixels)
                {
                    //récupérer la couleur du pixel
                    int[] couleurRGB = Outils.retournerRGB(bfImage.getRGB(pixel.getX(),pixel.getY()));
                    //on récupére la bonne couleur
                    Color color = new Color(couleurRGB[0],couleurRGB[1], couleurRGB[2]);

                    double distance = Outils.distance(color,couleur);

                    if(distance<minDistance)
                    {
                        minDistance = distance;
                        bonneColor = color;
                    }
                }
                couleursFinal.put(bonneColor,pixels);
            }


            for (Map.Entry<Color, ArrayList<Pixel>> entry : hashMapColor.entrySet()) {
                Color couleur = entry.getKey();
                ArrayList<Pixel> pixels = entry.getValue();

                for (Pixel pixel : pixels) {
                    copie.setRGB(pixel.getX(),pixel.getY(),couleur.getRGB());
                }
            }
            return copie;
    }
}
