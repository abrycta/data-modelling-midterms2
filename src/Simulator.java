import java.util.ArrayList;
public class Simulator {

    /**
     * Simulates the Number of Minutes
     * @param numberOfMinutes = number of minutes where the simulation will terminate
     * @return arrayList of parts
     */
    public ArrayList<Part> simulateMinutes(int numberOfMinutes) {
        ArrayList<Part> partsArrayList = new ArrayList<>();
        int currentTime = 0;

        //  Simulate the specified number of minutes
        while (currentTime < numberOfMinutes) {
            // Create a new customer object
            Part parts = new Part();
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
        
        public int calculateTime() {

        }

        public String checkEventType() {

        }

        public int calculateVariableQ() {

        }

        public int calculateVariableB() {

        }

        public int calculateArrivalTimeInQueue() {

        }

        public int calculateArrivalTimeInService() {

        }

        /*
                FOR STATISTICAL ACCUMULATORS
                     P = numberOfParts
                     N = partsThatPassQueue
                     ΣWQ = totalWaitingTime
                     WQ* = maxWaitingTime
                     ΣTS = totalTimeInSystem
                     TS* = maxTimeInSystem
                     ∫Q = integralQ
                     Q* = maxQ
                     ∫B = integralB
                     B* = maxB
         */

        public int calculateNumberOfParts() {

        }

        public int calculatePartsThatPassQueue() {

        }

        public int calculateTotalWaitingTime() {

        }

        public int calculateMaxWaitingTime() {

        }

        public int calculateTotalTimeInSystem() {

        }

        public int calculateMaxTimeInSystem() {

        }

        public int calculateIntegralQ() {

        }

        public int calculateMaxQ() {

        }

        public int calculateIntegralB() {

        }

        public int calculateMaxB() {

        }

    }
}
