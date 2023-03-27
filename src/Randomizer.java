import java.util.Random;

public class Randomizer {
    private static final Random RAND = new Random();

    public static int lookupInterArrivalTime() {
        double mean = 0.0;
        double standardDeviation = 1.0;
        int min = 1;
        int max = 8;
        int randomValue = (int) (mean + RAND.nextGaussian() * standardDeviation);
        return Math.max(min, Math.min(max, randomValue));
    }

    public static int lookUpServiceTime() {
        double mean = 0.0;
        double standardDeviation = 1.0;
        int min = 1;
        int max = 5;
        int randomValue = (int) (mean + RAND.nextGaussian() * standardDeviation);
        return Math.max(min, Math.min(max, randomValue));
    }
}

