import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Ancheta, Charles
 * @author Bonifacio, Jade
 * @author Bustarde, Jerome
 * @author Nudo, Kurt
 */
public class Randomizer {
    /**
     *
     * @return random number ranging from 1-8, with each having 0.125 probability.
     */
    public static int lookUpArrival() {
        HashMap<Integer, Double> arrivalDistribution = new HashMap<>();
        arrivalDistribution.put(1, 0.125);
        arrivalDistribution.put(2, 0.125);
        arrivalDistribution.put(3, 0.125);
        arrivalDistribution.put(4, 0.125);
        arrivalDistribution.put(5, 0.125);
        arrivalDistribution.put(6, 0.125);
        arrivalDistribution.put(7, 0.125);
        arrivalDistribution.put(8, 0.125);

        return getRandomNumber(arrivalDistribution);
    }

    /**
     *
     * @return random number between 1-5 based on the probabilities
     * specified in the given hashmap.
     */
    public static int lookUpService() {
        HashMap<Integer, Double> serviceDistribution = new HashMap<>();
        serviceDistribution.put(1, 0.15);
        serviceDistribution.put(2, 0.30);
        serviceDistribution.put(3, 0.25);
        serviceDistribution.put(4, 0.20);
        serviceDistribution.put(5, 0.10);

        return getRandomNumber(serviceDistribution);
    }

    /**
     *
     * @param map a HashMap where the keys are integers and the values
     *            represent the probability of that key being the output.
     * @return an integer representing the randomly generated output.
     */
    public static int getRandomNumber(Map<Integer, Double> map) {
        Random rand = new Random();
        double prob = rand.nextDouble();
        double cumulativeProb = 0.0;
        int output = 0;

        for (int key : map.keySet()) {
            cumulativeProb += map.get(key);
            if (prob < cumulativeProb) {
                output = key;
                break;
            }
        }
        return output;
    }
}
