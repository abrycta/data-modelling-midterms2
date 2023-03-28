import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

public class Randomizer {

    private static final Random RAND = new Random();

    public static double lookupInterArrivalTime() {
        double mean = 10.0;
        double standardDeviation = 5.5;
        double randomValue = mean + RAND.nextGaussian() * standardDeviation;
        while (randomValue < 1 || randomValue > 15){
            randomValue = mean + RAND.nextGaussian() * standardDeviation;
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        String roundedValue = decimalFormat.format(randomValue);
        return Double.parseDouble(roundedValue);
    }

    public static double lookUpServiceTime() {
        double mean = 8.0;
        double standardDeviation = 1.5;
        double randomValue = mean + RAND.nextGaussian() * standardDeviation;
        while (randomValue < 1 || randomValue > 15){
            randomValue = mean + RAND.nextGaussian() * standardDeviation;
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        String roundedValue = decimalFormat.format(randomValue);
        return Double.parseDouble(roundedValue);
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




