package TP;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main_Q3 {

    public static void main(String[] args) {

        try {
            BufferedImage bfImg = ImageIO.read(new File("./images/images_etudiants/originale.jpg"));
            BufferedImage retour = new BufferedImage(bfImg.getWidth(), bfImg.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

            for (int i = 0; i < bfImg.getHeight(); i++) {
                for (int j = 0; j < bfImg.getWidth(); j++) {

                    int[] tab = retourRGB(bfImg.getRGB(j,i));
                    int moyenne = (tab[0]+tab[1]+tab[2])/tab.length;
                    Color color = new Color(moyenne,moyenne,moyenne);

                    retour.setRGB(j, i, color.getRGB());

                }
            }

            ImageIO.write(retour,"PNG",new File("./images/resultats/resultatQ3.png"));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static int[] retourRGB(int rgb){
        int blue = rgb & 0xff;
        int green = (rgb & 0xff00) >> 8;
        int red = (rgb & 0xff0000) >> 16;
        return new int[]{red,green,blue};
    }

}
