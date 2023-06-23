package SAE.tests;

import SAE.algoProf.ResolutionImage;
import SAE.nosAlgo.SolutionHistogramme;
import SAE.nosAlgo.SolutionHistogrammePlus;
import SAE.nosAlgo.SolutionOptimisation;
import SAE.nosAlgo.SolutionSpectrale;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static SAE.nosAlgo.Outils.distance;

public class Tests {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("utilisation : java MaSolution [chemin image depuis ./images/] [nombre de couleurs]");
            return;
        }

        // nombre de couleurs
        int nombreDeCouleurs = Integer.parseInt(args[1]);

        try {
            // lecture de l'image d'origine
            BufferedImage bfImg = ImageIO.read(new File("./images/" + args[0]));
            // somme des temps d'execution
            long temps = 0;
            // somme des qualités de rendu
            long qualité = 0;
            // 20 essais
            int nbEssais = 20;
            // affichage compréhensible des long
            NumberFormat nf = NumberFormat.getInstance(Locale.US);

            BufferedImage retour;

            //Pour chaque algo, afficher le temps d excution moyen et la qualité

            /*       Solution Spectrale        */
            {
                SolutionSpectrale ss = new SolutionSpectrale();
                // un seul essais car pas d'utilsation de random
                // Calcul du temps
                // lancement du chrono
                long lStartTime = System.nanoTime();
                // Execution
                retour = ss.resoudre(nombreDeCouleurs, bfImg);
                // arret du chrono
                long lEndTime = System.nanoTime();
                temps += lEndTime - lStartTime;

                //Calcul de la qualité
                qualité += distance(bfImg, retour);

                temps = TimeUnit.MILLISECONDS.convert(temps,TimeUnit.NANOSECONDS);

                System.out.println("/*       Solution spectrale :        */");
                System.out.println("    Moyenne temps : " + temps +" millisec");
                System.out.println("    Moyenne qualite : " + nf.format(qualité) + "\n");

                //reinitialisation
                temps=0;
                qualité=0;
            }

            /*       Solution Histogramme        */
            {
                SolutionHistogramme sh = new SolutionHistogramme();
                // un seul essais car pas d'utilsation de random
                // Calcul du temps
                // lancement du chrono
                long lStartTime = System.nanoTime();
                // Execution
                retour = sh.resoudre(nombreDeCouleurs, bfImg);
                // arret du chrono
                long lEndTime = System.nanoTime();
                temps += lEndTime - lStartTime;

                //Calcul de la qualité
                qualité += distance(bfImg, retour);

                temps = TimeUnit.MILLISECONDS.convert(temps,TimeUnit.NANOSECONDS);

                System.out.println("/*       Solution Histogramme :        */");
                System.out.println("    Moyenne temps : " + temps +" millisec");
                System.out.println("    Moyenne qualite : " + nf.format(qualité) + "\n");

                //reinitialisation
                temps=0;
                qualité=0;
            }


            /*       Solution HistogrammePlus        */
            {
                SolutionHistogrammePlus shp = new SolutionHistogrammePlus();
                // un seul essais car pas d'utilsation de random
                // Calcul du temps
                // lancement du chrono
                long lStartTime = System.nanoTime();
                // Execution
                retour = shp.resoudre(nombreDeCouleurs, bfImg);
                // arret du chrono
                long lEndTime = System.nanoTime();
                temps += lEndTime - lStartTime;

                //Calcul de la qualité
                qualité += distance(bfImg, retour);

                temps = TimeUnit.MILLISECONDS.convert(temps,TimeUnit.NANOSECONDS);

                System.out.println("/*       Solution Histogramme Plus :        */");
                System.out.println("    Moyenne temps : " + temps +" millisec");
                System.out.println("    Moyenne qualite : " + nf.format(qualité) + "\n");

                //reinitialisation
                temps=0;
                qualité=0;
            }


            /*       Solution Optimisation        */
            {
                SolutionOptimisation so = new SolutionOptimisation();
                // Plusieurs essais car utilisation de random
                for (int i = 0; i < nbEssais; i++) {
                    // Calcul du temps
                    // lancement du chrono
                    long lStartTime = System.nanoTime();
                    // Execution
                    retour = so.resoudre(nombreDeCouleurs, bfImg);
                    // arret du chrono
                    long lEndTime = System.nanoTime();
                    temps += lEndTime - lStartTime;

                    //Calcul de la qualité
                    qualité += distance(bfImg, retour);

                }

                temps = TimeUnit.MILLISECONDS.convert(temps/nbEssais,TimeUnit.NANOSECONDS);

                System.out.println("/*       Solution Optimisation :        */");
                System.out.println("    Moyenne temps : " + temps +" millisec");
                System.out.println("    Moyenne qualite : " + nf.format(qualité / nbEssais) + "\n");

                //reinitialisation
                temps=0;
                qualité=0;
            }

            /*       Solution KMeans        */
            {
                ResolutionImage ri = new ResolutionImage();
                // Plusieurs essais car utilisation de random
                for (int i = 0; i < nbEssais; i++) {
                    // Calcul du temps
                    // lancement du chrono
                    long lStartTime = System.nanoTime();
                    // Execution
                    retour = ri.resoudre(nombreDeCouleurs, bfImg);
                    // arret du chrono
                    long lEndTime = System.nanoTime();
                    temps += lEndTime - lStartTime;

                    //Calcul de la qualité
                    qualité += distance(bfImg, retour);

                }

                temps = TimeUnit.MILLISECONDS.convert(temps/nbEssais,TimeUnit.NANOSECONDS);

                System.out.println("/*       Solution KMeans :        */");
                System.out.println("    Moyenne temps : " + temps +" millisec");
                System.out.println("    Moyenne qualite : " + nf.format(qualité / nbEssais) + "\n");

                //reinitialisation
                temps=0;
                qualité=0;
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
