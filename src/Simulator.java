import java.util.ArrayList;
public class Simulator {

    /**
     * Simulates the Number of Minutes
     * @param numberOfMinutes = number of minutes where the simulation will terminate
     * @return arrayList of parts
     */
    public ArrayList<Event> simulateMinutes(int numberOfMinutes) {
        ArrayList<Event> eventArrayList = new ArrayList<>();
        int currentTime = 0;

        //  Simulate the specified number of minutes
        while (currentTime < numberOfMinutes) {
            // Create a new customer object
            Event events = new Event();
            // Set customer ID
            events.setEventID(eventArrayList.size() + 1);

            // Set values for the first customer, else set values for succeeding customers
            if (eventArrayList.isEmpty()) {
                events.setTime(0);
                events.setEventType(0); // 0 = Initialize, 1 = Arrival, 2 = Departure
                events.setTimeInQueue(0);
                events.setNumberOfPartsInQueue(0);
                events.setUtilization(0);
                events.setPartArrivalTime(0);
                events.setServiceTime(0);
                events.setPartsProducedSoFar(0); // P
                events.setNumberOfPartsThatPassedThroughTheQueueSoFar(0); // N
                events.setWaitingTimeInQueueSoFar(0); // ΣWQ
                events.setLongestTimeSpentInQueueSoFar(0); // WQ*
                events.setTotalTimeSpentInSystemByAllPartsThatHaveDeparted(0); // ΣTS
                events.setLongestTimeInSystem(0); // TS*
                events.setAreaUnderQueueLengthCurve(0); // ∫Q
                events.setHighestLevelOfQ(0); // Q*
                events.setAreaUnderServerBusy(0); // ∫B
            } else {
                events.setTime(calculateTime(events.getTime()));
                events.setEventType(checkEventType(events.getEventType())); // 0 = Initialize, 1 = Arrival, 2 = Departure
                events.setTimeInQueue(calculateTimeInQueue(events.getTimeInQueue()));
                events.setNumberOfPartsInQueue(calculateNumberOfPartsInQueue(events.getNumberOfPartsInQueue()));
                events.setUtilization(calculateUtilization(events.getUtilization()));
                events.setPartArrivalTime(calculatePartArrivalTime(events.getPartArrivalTime()));
                events.setServiceTime(calculatePartServiceTime(events.getServiceTime()));
                events.setPartsProducedSoFar(calculatePartsProducedSoFar(events.getPartsProducedSoFar())); // P
                events.setNumberOfPartsThatPassedThroughTheQueueSoFar(calculateNumberOfPartsThatPassedThroughTheQueueSoFar(events.getNumberOfPartsThatPassedThroughTheQueueSoFar())); // N
                events.setWaitingTimeInQueueSoFar(calculateWaitingTimeInQueueSoFar(events.getWaitingTimeInQueueSoFar())); // ΣWQ
                events.setLongestTimeSpentInQueueSoFar(calculateLongestTimeSpentInQueueSoFar(events.getLongestTimeSpentInQueueSoFar())); // WQ*
                events.setTotalTimeSpentInSystemByAllPartsThatHaveDeparted(calculateTotalTimeSpentInSystemByAllPartsThatHaveDeparted(events.getTotalTimeSpentInSystemByAllPartsThatHaveDeparted())); // ΣTS
                events.setLongestTimeInSystem(calculateLongestTimeInSystem(events.getLongestTimeInSystem())); // TS*
                events.setAreaUnderQueueLengthCurve(calculateAreaUnderQueueLengthCurve(events.getAreaUnderQueueLengthCurve())); // ∫Q
                events.setHighestLevelOfQ(calculateHighestLevelOfQ(events.getHighestLevelOfQ())); // Q*
                events.setAreaUnderServerBusy(calculateAreaUnderServerBusy(events.getAreaUnderServerBusy())); // ∫B
            }
            eventArrayList.add(events);
            // currentTime = next arrival time
        }
    return eventArrayList;
    }

    public double calculateTime(double time) {
        return time;
    }

    public int checkEventType(int eventType) {
        return eventType;
    }

    public double calculateTimeInQueue(double timeInQueue) {
        return timeInQueue;
    }

    public double calculateNumberOfPartsInQueue(double numberOfPartsInQueue) {
        return numberOfPartsInQueue;
    }

    public int calculateUtilization(int utilization) {
        return utilization;
    }

    public double calculatePartArrivalTime(double partArrivalTime) {
        return partArrivalTime;
    }

    public double calculatePartServiceTime(double serviceTime) {
        return serviceTime;
    }

    /*
          FOR STATISTICAL ACCUMULATORS
               P = calculatePartsProducedSoFar
               N = calculateNumberOfPartsThatPassedThroughTheQueueSoFar
               ΣWQ = calculateWaitingTimeInQueueSoFar
               WQ* = calculateLongestTimeSpentInQueueSoFar
               ΣTS = calculateTotalTimeSpentInSystemByAllPartsThatHaveDeparted
               TS* = calculateLongestTimeInSystem
               ∫Q = calculateAreaUnderQueueLengthCurve
               Q* = calculateHighestLevelOfQ
               ∫B = calculateAreaUnderServerBusy
    */

    public int calculatePartsProducedSoFar(int partsProducedSoFar) {
        return partsProducedSoFar;
    }

    public int calculateNumberOfPartsThatPassedThroughTheQueueSoFar(int numberOfPartsThatPassedThroughTheQueueSoFar) {
        return numberOfPartsThatPassedThroughTheQueueSoFar;
    }

    public double calculateWaitingTimeInQueueSoFar(double waitingTimeInQueueSoFar) {
        return waitingTimeInQueueSoFar;
    }

    public double calculateLongestTimeSpentInQueueSoFar(double longestTimeSpentInQueueSoFar) {
        return longestTimeSpentInQueueSoFar;
    }

    public double calculateTotalTimeSpentInSystemByAllPartsThatHaveDeparted(double totalTimeSpentInSystemByAllPartsThatHaveDeparted) {
        return totalTimeSpentInSystemByAllPartsThatHaveDeparted;
    }

    public double calculateLongestTimeInSystem(double longestTimeInSystem) {
        return longestTimeInSystem;
    }

    public double calculateAreaUnderQueueLengthCurve(double areaUnderQueueLengthCurve) {
        return areaUnderQueueLengthCurve;
    }

    public double calculateHighestLevelOfQ(double highestLevelOfQ) {
        return highestLevelOfQ;
    }

    public double calculateAreaUnderServerBusy(double areaUnderServerBusy) {
        return areaUnderServerBusy;
    }

}
