package TP;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main_Q6 {

    public static void main(String[] args) {
        long retour = 0;
        try {

            retour = Distance.distance(ImageIO.read(new File("./images/images_etudiants/originale.jpg")) , ImageIO.read(new File("./images/images_etudiants/copie_proche_YGWOP.png")) );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("distance total : "+retour);
    }

}
