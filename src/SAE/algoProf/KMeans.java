package SAE.algoProf;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class KMeans {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("utilisation : java MaSolution [chemin image depuis ./images/] [nombre de couleurs]");
            return;
        }

        // nombre de couleurs
        int nombreDeCouleurs = Integer.parseInt(args[1]);

        // boucler pour stats
        for (int i = 0; i < 4; i++) {

            ResolutionImage ri = new ResolutionImage();
            BufferedImage retour = ri.creerImage(nombreDeCouleurs, args[0], ResolutionImage.getRandom());

            ImageIO.write(retour,"PNG",new File("./images/resultats/resultatMaSolution"+i+".png"));

        }
    }


}
