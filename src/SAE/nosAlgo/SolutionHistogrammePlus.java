package SAE.nosAlgo;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import static SAE.nosAlgo.Outils.evaluer;

public class SolutionHistogrammePlus implements Solution {
    public  BufferedImage resoudre(int nombreDeCouleurs, BufferedImage bfImg) {

        // min et max : int des couleurs situées au extrémité de la plage de couleur de l'image.
        Color max=null, min=null;

        // un tableau de couleurs à remplir afin d'avoir les couleurs prédéfinies
        Color[] couleurs = new Color[nombreDeCouleurs];

        BufferedImage retour = new BufferedImage(bfImg.getWidth(), bfImg.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        TreeMap<Color, Integer> utilisations = new TreeMap<Color, Integer>(new Comparator<Color>() {
            @Override
            public int compare(Color c1, Color c2) {
                return c1.toString().compareTo(c2.toString());
            }
        });

        //on classe les couleurs selon la plus utilisée.
        for (int i = 0; i < bfImg.getHeight(); i++) {
            for (int j = 0; j < bfImg.getWidth(); j++) {
                // on recupère la couleur aux coordonnées précises
                Color couleurRGB = new Color(bfImg.getRGB(j, i));

                // si elle est deja presente dans la map alors on incremente
                int nb = 1;
                if (utilisations.containsKey(couleurRGB)){
                    nb += utilisations.get(couleurRGB);
                }
                // on l'ajoute ou modifie la valeur dans la map
                utilisations.put(couleurRGB,nb);

                if (min == null){
                    min = couleurRGB;
                    max = couleurRGB;
                }else {
                    if (min.getRGB() > couleurRGB.getRGB())
                        min = couleurRGB;
                    if (max.getRGB() < couleurRGB.getRGB())
                        max = couleurRGB;
                }
            }
        }
        int ecart = evaluer(min,max);
        // calculer l'ecart qu il doit y avoir entre chaque couleur
        int portion = ecart/(3*nombreDeCouleurs);

        //trier la map
        // Convertir la map en une List de "Map.Entry"
        List<Map.Entry<Color, Integer>> list = new ArrayList<>(utilisations.entrySet());

        // Trier la liste par valeur
        Collections.sort(list, new Comparator<Map.Entry<Color, Integer>>() {
            public int compare(Map.Entry<Color, Integer> o1, Map.Entry<Color, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        // Créer une nouvelle LinkedHashMap pour conserver l'ordre des valeurs
        Map<Color, Integer> sortedMap = new LinkedHashMap<Color, Integer>();
        for (Map.Entry<Color, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        // Afficher la taille de la map triée
        //System.out.println(sortedMap.size());

        // Obtenir un Iterator
        Iterator<Map.Entry<Color, Integer>> iterator = utilisations.entrySet().iterator();

        //on remplit le tableau de couleur
        while(couleurs[nombreDeCouleurs-1]==null && iterator.hasNext()){
            Map.Entry<Color, Integer> entry = iterator.next();

            //on verifie pour chacune des couleurs dans le tableau, la distance avec la nouvelle couleur
            boolean trouver = false;
            for (int i = 0; i < couleurs.length; i++) {
                Color c = couleurs[i];
                if (c!=null && !trouver){
                    trouver= evaluer(c,entry.getKey())<portion || trouver;
                }else if (c==null && !trouver){
                    couleurs[i]=entry.getKey();
                    trouver=true;
                }
            }
        }

        //afficher le tableau de couleurs
        //for (Color c: couleurs) {
        //  System.out.println(c);
        //}


        //main Q5
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
        return retour;
    }
}
