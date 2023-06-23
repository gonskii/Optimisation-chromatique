package SAE.tests;

import SAE.Solution;
import SAE.algoProf.ResolutionImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainSolution {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("utilisation : java MaSolution [chemin image depuis ./images/] [nombre de couleurs]");
            return;
        }

        // nombre de couleurs
        int nombreDeCouleurs = Integer.parseInt(args[1]);

        try {
            // lecture de l'image d'origine
            BufferedImage bfImg = ImageIO.read(new File("./images/" + args[0]));

            /*          On choisit la soulution que l'on souhaite utiliser               */
            //Solution s = new SolutionSpectrale(); // Spectrale
            //Solution s = new SolutionHistogramme(); // Histogramme
            //Solution s = new SolutionHistogrammePlus(); //Histogramme Plus
            //Solution s = new SolutionOptimisation(); // Optimisation
            Solution s = new ResolutionImage(); // KMeans

            BufferedImage retour = s.resoudre(nombreDeCouleurs, bfImg);

            ImageIO.write(retour, "PNG", new File("./images/resultats/resultatMaSolution.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
