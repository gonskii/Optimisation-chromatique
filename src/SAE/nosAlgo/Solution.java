package SAE.nosAlgo;

import java.awt.image.BufferedImage;

public interface Solution
{
    /**
     * Méthode qui permet de résoudre le problème
     * @param nombreDeCouleurs, nombre de couleurs souhaite
     * @param bfImage, le buffer de l'image
     * @return , retourne le buffer de l'image finale avec le nombre de couleurs souhaite
     */
    public  BufferedImage resoudre(int nombreDeCouleurs, BufferedImage bfImage);
}
