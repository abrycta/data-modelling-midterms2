import java.util.Random;

public class Randomizer {

    private static final Random RAND = new Random();

    public static double lookupInterArrivalTime() {
        double mean = 10.0;
        double standardDeviation = 4.0;
        int min = 1;
        int max = 10;
        double randomValue = mean + RAND.nextGaussian() * standardDeviation;
        randomValue = Math.max(min, Math.min(max, randomValue));
        return Math.round(randomValue * 100.0) / 100.0;
    }

    public static double lookUpServiceTime() {
        double mean = 10;
        double standardDeviation = 4.0;
        int min = 1;
        int max = 8;
        double randomValue = mean + RAND.nextGaussian() * standardDeviation;
        randomValue = Math.max(min, Math.min(max, randomValue));
        return Math.round(randomValue * 100.0) / 100.0;
    }
    //test
    public static void main(String[] args) {
        System.out.println("inter arrival time");
        for (int i = 0; i < 10; i++) {
            System.out.println(lookupInterArrivalTime());
        }

        System.out.println("service time");
        for (int i = 0; i < 10; i++) {
            System.out.println(lookUpServiceTime());
        }
    }
}




