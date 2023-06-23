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

        // image de base
        BufferedImage image = ImageIO.read(new File("./images/" + args[0]));
        ResolutionImage ri = new ResolutionImage();

        // boucler pour stats
        for (int i = 0; i < 4; i++) {
            BufferedImage retour = ri.resoudre(nombreDeCouleurs, image);

            ImageIO.write(retour, "PNG", new File("./images/resultats/resultatMaSolution" + i + ".png"));

        }
    }

}
