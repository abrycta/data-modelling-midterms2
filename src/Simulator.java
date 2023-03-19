import java.util.ArrayList;
public class Simulator {

    /**
     * Simulates the Number of Minutes
     * @param numberOfMinutes = number of minutes where the simulation will terminate
     * @return arrayList of parts
     */
    public ArrayList<Parts> simulateMinutes(int numberOfMinutes) {
        ArrayList<Parts> partsArrayList = new ArrayList<>();
        int currentTime = 0;

        //  Simulate the specified number of minutes
        while (currentTime < numberOfMinutes) {
            // Create a new customer object
            Parts parts = new Parts();
            // Set customer ID
            parts.setEntityNum(partsArrayList.size() + 1);

            // Set values for the first customer, else set values for succeeding customers
            if (partsArrayList.isEmpty()) {
                parts.setTime(0);
                parts.setEventType("Initialize");
                parts.setVariableQ(0);
                parts.setVariableB(0);
                parts.setArrivalTimeInQueue(0);
                parts.setArrivalTimeInService(0);
            }else {
                parts.setTime();
                // Arrival or Departure for Event type
                parts.setEventType();
                parts.setVariableQ();
                parts.setVariableB();
                parts.setArrivalTimeInQueue();
                parts.setArrivalTimeInService();
            }
        }
        // numOfParts
        // partsThatPassQueue
        // totalWaitingTime
        // maximumWaitingTime
        // totalTimeInSystem
        // maximumTimeInSystem
    }
}
