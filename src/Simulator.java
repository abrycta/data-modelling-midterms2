import java.util.ArrayList;
public class Simulator {

    /**
     * Simulates the Number of Minutes
     * @param numberOfMinutes = number of minutes where the simulation will terminate
     * @return arrayList of parts
     */
    public ArrayList<Event> simulateMinutes(int numberOfMinutes) {
        ArrayList<Event> eventArrayList = new ArrayList<>();
        ArrayList<Double> initialTimesInQueue = new ArrayList<>();
        initialTimesInQueue.add(0.0);
        ArrayList<Integer> eventIDsInQueue = new ArrayList<>();
        ArrayList<Integer> eventIdsDeparted = new ArrayList<>();
        ArrayList<Integer> eventIdsPassedQueue = new ArrayList<>();
        int currentTime = 0;

        //  Simulate the specified number of minutes
        while (currentTime < numberOfMinutes) {
            // Create a new customer object
            Event event = new Event();
            // Set customer ID

            // Set values for the first customer, else set values for succeeding customers
            if (eventArrayList.isEmpty()) {
                event.setEventID(0);
                event.setTime(0);
                event.setEventType(0); // 0 = Initialize, 1 = Arrival, 2 = Departure
                event.setNumberOfPartsInQueue(0);
                event.setUtilization(0);
                event.setTimesInQueue(initialTimesInQueue);
                event.setPartInServiceTime(0);

                event.setPartsProducedSoFar(0); // P
                event.setNumberOfPartsThatPassedThroughTheQueueSoFar(0); // N
                event.setLongestTimeSpentInQueueSoFar(0); // WQ*
                event.setWaitingTimeInQueueSoFar(0); // ΣWQ
                event.setLongestTimeInSystem(0); // TS*
                event.setTotalTimeSpentInSystemByAllPartsThatHaveDeparted(0); // ΣTS
                event.setAreaUnderQueueLengthCurve(0); // ∫Q
                event.setHighestLevelOfQ(0); // Q*
                event.setAreaUnderServerBusy(0); // ∫B
            } else {
                event.setEventID(calculateEventID(eventArrayList));

                event.setTime(calculateTime(getPrevTimesInQueue(eventArrayList),
                        getPrevPartInServiceTime(eventArrayList)));

                event.setEventType(getEventType()); // 0 = Initialize, 1 = Arrival, 2 = Departure

                // Stores event IDs that are in queue
                if (event.getEventType() == 1) {
                    eventIDsInQueue.add(event.getEventID());
                    if(event.getEventID() == 1){
                        eventIdsPassedQueue.add(event.getEventID());
                    }
                } else if (event.getEventType() == 0){
                    eventIDsInQueue.remove(event.getEventID());
                    eventIdsDeparted.add(event.getEventID());
                    eventIdsPassedQueue.add(event.getEventID());

                }

                event.setNumberOfPartsInQueue(eventIDsInQueue.size()-1);

                if (eventIDsInQueue.isEmpty()){
                    event.setUtilization(0);
                } else {
                    event.setUtilization(1);
                }



                event.setTimesInQueue(calculateTimesInQueue(eventIDsInQueue, eventArrayList));

                event.setPartInServiceTime(calculatePartInServiceTime(event.getEventType(),
                        eventIDsInQueue, eventArrayList));

                // Statistical Accumulators
                event.setPartsProducedSoFar(eventIdsDeparted.size()); // P

                event.setNumberOfPartsThatPassedThroughTheQueueSoFar(eventIdsPassedQueue.size()); // N

                event.setLongestTimeSpentInQueueSoFar(calculateLongestTimeSpentInQueueSoFar(event.getPartInServiceTime(),
                        event.getTime(), event.getEventType(), getPrevWQ(eventArrayList)));// WQ*

                event.setWaitingTimeInQueueSoFar(calculateWaitingTimeInQueueSoFar(event.getEventType(),
                        getPrevWaitingTimeQueueSoFar(eventArrayList),event.getLongestTimeSpentInQueueSoFar())); // ΣWQ


                event.setLongestTimeInSystem(calculateLongestTimeInSystem(event.getEventType(), event.getEventID(),
                        eventArrayList, getPrevTS(eventArrayList))); // TS*

                event.setTotalTimeSpentInSystemByAllPartsThatHaveDeparted(
                        calculateTotalTimeSpentInSystemByAllPartsThatHaveDeparted(event.getEventType(),
                                event.getEventID(), getPrevSigmaTS(eventArrayList),event.getLongestTimeInSystem())); // ΣTS

//                event.setAreaUnderQueueLengthCurve(calculateAreaUnderQueueLengthCurve(event.getAreaUnderQueueLengthCurve())); // ∫Q
//                event.setHighestLevelOfQ(calculateHighestLevelOfQ(event.getHighestLevelOfQ())); // Q*
//                event.setAreaUnderServerBusy(calculateAreaUnderServerBusy(event.getAreaUnderServerBusy())); // ∫B
            }
            eventArrayList.add(event);
            // currentTime = next arrival time
        }
    return eventArrayList;
    }

    // returns prev event times in queue
    public ArrayList<Double> getPrevTimesInQueue(ArrayList<Event> eventArrayList) {
        ArrayList<Double> timesInQueue = new ArrayList<>();
        return timesInQueue;
    }

    // returns prev part in service time
    public double getPrevPartInServiceTime(ArrayList<Event> eventArrayList) {
        double partInServiceTime = 0;
        return partInServiceTime;
    }

    public double getPrevWaitingTimeQueueSoFar(ArrayList<Event> eventArrayList) {
        int waitingTimeQueueSoFar = 0;
        return waitingTimeQueueSoFar;
    }

    public double getPrevSigmaTS(ArrayList<Event> eventArrayList) {
        double sigmaTS = 0;
        return sigmaTS;
    }

    public double getPrevWQ(ArrayList<Event> eventArrayList) {
        return 0;
    }

    public double getPrevTS(ArrayList<Event> eventArrayList) {
        return 0;
    }

    // Returns the eventID
    public int calculateEventID(ArrayList<Event> eventArrayList) {
        int eventID = 0;
        return eventID;
    }

    //
    public double calculateTime(ArrayList<Double> prevTimesInQueue, double prevPartInServiceTime) {
        double time =0;
        return time;
    }

    public int getEventType() {
        int eventType =0;
        return eventType;
    }


    // removes queue if event is departure and adds queue if arrival
    // keep the index of the event id and queue same to its queue
    // subtact 1 to the total events in Queue (will return empty arraylist if eventIDs in Queue is 1)
    public ArrayList<Double> calculateTimesInQueue(ArrayList<Integer> eventIDsInQueue, ArrayList<Event> eventArrayList) {
        ArrayList<Double> timesInQueue = new ArrayList<>();
        return timesInQueue;
    }

    // returns the service time of the event in front of the queue
    // retain value if event is arrival
    public double calculatePartInServiceTime(double partInServiceTime,
                                             ArrayList<Integer> eventIDsInQueue, ArrayList<Event> eventArrayList) {
        double serviceTime = 0;
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

    // retain values if event type is arrival
    // WQ*
    public double calculateLongestTimeSpentInQueueSoFar(double partInServiceTime,
                                                        double eventTime, int eventType, double prevWQ) {
        double longestTimeSpentInQueueSoFar = 0;
        return longestTimeSpentInQueueSoFar;
    }

    // retain values f event is arrival
    // sigma WQ
    public double calculateWaitingTimeInQueueSoFar(int eventType, double prevWaitingTimeInQueueSoFar, double WQ) {
        double waitingTimeInQueueSoFar = 0;
        return waitingTimeInQueueSoFar;
    }

    // retain values if event type is arrival
    // TS*
    public double calculateLongestTimeInSystem(int eventType, int eventID, ArrayList<Event> eventArrayList,
                                               double prevTS) {
        double longestTimeInSystem = 0;
        return longestTimeInSystem;
    }

    // retain values if event is arrival
    // sigma TS
    public double calculateTotalTimeSpentInSystemByAllPartsThatHaveDeparted(int eventType, int eventID,
                                                                            double prevSigmaTS, double TS) {
        double totalTimeSpentInSystemByAllPartsThatHaveDeparted = 0;
        return totalTimeSpentInSystemByAllPartsThatHaveDeparted;
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
