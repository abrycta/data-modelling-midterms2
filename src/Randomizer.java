import java.util.Random;

public class Randomizer {
    private static final Random RAND = new Random();

    public static double lookupInterArrivalTime() {
        double mean = 0.0;
        double standardDeviation = 1.0;
        int min = 1;
        int max = 8;
        double randomValue = (mean + RAND.nextGaussian() * standardDeviation);
        return Math.max(min, Math.min(max, randomValue));
    }

    public static double lookUpServiceTime() {
        double mean = 0.0;
        double standardDeviation = 1.0;
        int min = 1;
        int max = 5;
        double randomValue =  (mean + RAND.nextGaussian() * standardDeviation);
        return Math.max(min, Math.min(max, randomValue));
    }
}

