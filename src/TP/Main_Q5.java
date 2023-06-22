package TP;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main_Q5 {

    public static void main(String[] args) {

        Color[] couleurs = new Color[]{Color.GREEN, Color.YELLOW, Color.WHITE, Color.ORANGE,Color.PINK};

        try {
            BufferedImage bfImg = ImageIO.read(new File("./images/images_etudiants/originale.jpg"));
            BufferedImage retour = new BufferedImage(bfImg.getWidth(), bfImg.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

            for (int i = 0; i < bfImg.getHeight(); i++) {
                for (int j = 0; j < bfImg.getWidth(); j++) {
                    
                    Color gardee = null;
                    int tmp = -1;
                    
                    for (Color c:couleurs ) {
                        int eval = evaluer(new Color(bfImg.getRGB(j,i)), c);
                        if (eval<tmp){
                            tmp=eval;
                            gardee=c;
                        } else if (tmp==-1){
                            tmp=eval;
                            gardee=c;
                        }
                            
                    }
                    
                    retour.setRGB(j, i, gardee.getRGB());

                }
            }

            ImageIO.write(retour,"PNG",new File("./images/resultats/resultatQ5.png"));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static int evaluer(Color c1, Color c2){
        int[] tabC1 = retournerRGB(c1.getRGB());
        int[] tabC2 = retournerRGB(c2.getRGB());

        int d=0;
        for (int i = 0; i < 3; i++) {
            d+=Math.pow((tabC1[i]-tabC2[i]),2);
        }
        return d;
    }

    public static int[] retournerRGB(int rgb){
        int blue = rgb & 0xff;
        int green = (rgb & 0xff00) >> 8;
        int red = (rgb & 0xff0000) >> 16;
        return new int[]{red,green,blue};
    }

}
