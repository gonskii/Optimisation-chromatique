package SAE.tests;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tests {

    public static void main(String[] args){

        if (args.length != 2){
            System.out.println("utilisation : java MaSolution [chemin image depuis ./images/] [nombre de couleurs]");
            return;
        }

        // nombre de couleurs
        int nombreDeCouleurs = Integer.parseInt(args[1]);

        try {
            // lecture de l'image d'origine
            BufferedImage bfImg = ImageIO.read(new File("./images/"+args[0]));


            BufferedImage retour=null;
            ImageIO.write(retour,"PNG",new File("./images/resultats/resultatMaSolution.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
