package TP;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main_Q1 {

    public static void main(String[] args) {

        try {

            BufferedImage bfImg = ImageIO.read(new File("./images/images_etudiants/originale.jpg"));
            ImageIO.write(bfImg,"PNG",new File("./images/resultats/resultatQ1.png"));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
