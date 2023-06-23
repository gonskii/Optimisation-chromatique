package SAE.nosAlgo;

import java.util.Random;

public class RandomInt
{
    public static Random instance = null;

    public static Random getRandom() {
        if (instance == null) {
            instance = new Random(1234567890);
        }
        return instance;
    }
}
