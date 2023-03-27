import java.util.Random;

public class Randomizer {

    private static final Random RAND = new Random();

    public static int lookupInterArrivalTime() {
        double mean = 10.0;
        double standardDeviation = 2.0;
        int min = 1;
        int max = 8;
        int randomValue = (int) (mean + RAND.nextGaussian() * standardDeviation);
        return Math.max(min, Math.min(max, randomValue));
    }

    public static int lookUpServiceTime() {
        double mean = 8.0;
        double standardDeviation = 2.0;
        int min = 1;
        int max = 5;
        int randomValue = (int) (mean + RAND.nextGaussian() * standardDeviation);
        return Math.max(min, Math.min(max, randomValue));
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




