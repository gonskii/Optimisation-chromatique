package SAE.nosAlgo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class SolutionOptimisation {
    public static void main(String[] args) {
        int nbCouleur = Integer.parseInt(args[0]);

        try {
            BufferedImage image = ImageIO.read(new File("./images/images_etudiants/originale.jpg"));
            BufferedImage copie = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

            HashMap<Color, ArrayList<Pixel>> couleursFinal = new HashMap<>();

            Random random = RandomInt.getRandom();
            ArrayList<Color> couleurs = new ArrayList<Color>();
            HashSet<Integer> pixelUse = new HashSet<>();
            double minCouleurDistance = 600.0;


            while(couleurs.size()<nbCouleur)
            {
                // choisir un pixel au hasard
                int x = random.nextInt(image.getWidth());
                int y = random.nextInt(image.getHeight());

                int pixelIndex = y * image.getWidth() +x;

                if(pixelUse.contains(pixelIndex))
                {
                    continue;
                }

                pixelUse.add(pixelIndex);

                int rgb = image.getRGB(x, y);
                Color newColor = new Color(rgb);

                boolean goodDist = true;

                for (Color existingColor : couleurs) {
                    if (distance(newColor, existingColor) < minCouleurDistance) {
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
            for (int x = 0; x < image.getHeight(); x++) {
                for (int y = 0; y < image.getWidth(); y++)
                {
                    //on crée un objet pixel qui représente le pixel ou nous sommes présent
                    Pixel pixel = new Pixel(x,y);

                    //récupérer la couleur du pixel
                    int[] couleurRGB = couleurRGB(image.getRGB(pixel.getX(),pixel.getY()));

                    //on récupére la bonne couleur
                    Color color = new Color(couleurRGB[0],couleurRGB[1], couleurRGB[2]);
                    Color bonneColor = null;

                    double minDistance = Double.MAX_VALUE;

                    //Comparer a cette couleur a tout les couleurs qu'on a dans notre tableau pour l'ajoute a notre histogramme
                    for (Color couleur : couleurs)
                    {
                        //on crée une distance avec la méthode distance
                        double distance = distance(couleur,color);

                        //on compare avec min distance et on ajoute les bonnes couleurs
                        if(distance<minDistance)
                        {
                            minDistance = distance;
                            bonneColor = couleur;
                        }
                    }

                    //on ajoute dans l'histograme la bonne couleur avec le pixel associé:
                    if (hashMapColor.containsKey(bonneColor)) {
                        // Récupérez la liste des pixels associée à la couleur
                        ArrayList<Pixel> pixels = hashMapColor.get(bonneColor);
                        // Ajoutez le pixel à la liste des pixels
                        pixels.add(pixel);
                    } else {
                        // Créez une nouvelle liste de pixels et ajoutez le pixel actuel
                        ArrayList<Pixel> pixels = new ArrayList<Pixel>();
                        pixels.add(pixel);
                        // Ajoutez la couleur avec la liste des pixels dans l'histogramme
                        hashMapColor.put(bonneColor, pixels);
                    }

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
                    int[] couleurRGB = couleurRGB(image.getRGB(pixel.getX(),pixel.getY()));
                    //on récupére la bonne couleur
                    Color color = new Color(couleurRGB[0],couleurRGB[1], couleurRGB[2]);

                    double distance = distance(color,couleur);

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


            ImageIO.write(copie, "PNG", new File("./images/resultatQ5.png"));
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }


    public static double distance(Color a, Color b)
    {
        int[] rgbC1 = couleurRGB(a.getRGB());
        int[] rgbC2 = couleurRGB(b.getRGB());

        return (Math.pow(rgbC1[0]-rgbC2[0],2)+Math.pow(rgbC1[1]-rgbC2[1],2)+Math.pow(rgbC1[2]-rgbC2[2],2));
    }

    public static int[] couleurRGB(int rgb)
    {
        int blue = rgb & 0xff;
        int green = (rgb&0xff00) >> 8;
        int red = (rgb&0xff0000) >> 16;
        return new int[]{red, green, blue};
    }
}
