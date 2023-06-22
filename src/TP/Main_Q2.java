package TP;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main_Q2 {

    public static void main(String[] args) {

        try {
            BufferedImage bfImg = ImageIO.read(new File("./images/images_etudiants/originale.jpg"));
            BufferedImage retour = new BufferedImage(bfImg.getWidth(), bfImg.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

            for (int i = 0; i < bfImg.getHeight(); i++) {
                for (int j = 0; j < bfImg.getWidth(); j++) {

                    retour.setRGB(j, i, bfImg.getRGB(j,i));

                }
            }

            ImageIO.write(retour,"PNG",new File("./images/resultats/resultatQ2.png"));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
