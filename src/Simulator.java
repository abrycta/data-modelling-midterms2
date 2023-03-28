import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

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
        double currentTime = 0;
        ArrayList<Part> parts = simulateParts(numberOfMinutes);
        ArrayList<Event> partsCalendar = constructCalendar(parts);

        int counter = 0;
        //  Simulate the specified number of minutes
        while (currentTime < numberOfMinutes){
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
            } else{
                event.setEventID(partsCalendar.get(eventArrayList.size()-1).getEntityNumber());

                event.setTime(partsCalendar.get(eventArrayList.size()-1).getTime());



                event.setEventType(partsCalendar.get(eventArrayList.size()-1).getEventType()); // 0 = Initialize, 1 = Arrival, 2 = Departure

                // Stores event IDs that are in queue
                if (event.getEventType() == 1) {
                    eventIDsInQueue.add(event.getEventID());
                    if(event.getEventID() == 1){
                        eventIdsPassedQueue.add(event.getEventID());
                    }
                } else if (event.getEventType() == 2){
                    eventIDsInQueue.remove((Object) event.getEventID());
                    eventIdsDeparted.add(event.getEventID());
                    eventIdsPassedQueue.add(event.getEventID());

                }

                if (eventIDsInQueue.isEmpty()){
                    event.setNumberOfPartsInQueue(0);
                } else {
                    event.setNumberOfPartsInQueue(eventIDsInQueue.size()-1);
                }


                if (eventIDsInQueue.isEmpty()){
                    event.setUtilization(0);
                } else {
                    event.setUtilization(1);
                }



                event.setTimesInQueue(calculateTimesInQueue(eventIDsInQueue, eventArrayList, event));

                event.setPartInServiceTime(calculatePartInServiceTime(eventIDsInQueue, eventArrayList));

                // Statistical Accumulators
                event.setPartsProducedSoFar(eventIdsDeparted.size()); // P

                event.setNumberOfPartsThatPassedThroughTheQueueSoFar(eventIdsPassedQueue.size()); // N

                event.setLongestTimeSpentInQueueSoFar(calculateLongestTimeSpentInQueueSoFar(event.getPartInServiceTime(),
                        event.getTime(), event.getEventType(), getPrevWQ(eventArrayList)));// WQ*

                event.setWaitingTimeInQueueSoFar(calculateWaitingTimeInQueueSoFar(event.getEventType(),
                        getPrevWaitingTimeQueueSoFar(eventArrayList),event.getLongestTimeSpentInQueueSoFar(),
                        event.getPartInServiceTime())); // ΣWQ

                event.setLongestTimeInSystem(calculateLongestTimeInSystem(eventArrayList, event.getEventType(),
                        event.getEventID(), getPrevTS(eventArrayList), event.getTime())); //TS*

                event.setTotalTimeSpentInSystemByAllPartsThatHaveDeparted(
                        calculateTotalTimeSpentInSystemByAllPartsThatHaveDeparted(getPrevSigmaTS(eventArrayList),
                                event.getLongestTimeInSystem(), event.getEventType())); // ΣTS

                event.setAreaUnderQueueLengthCurve(calculateAreaUnderQueueLengthCurve(eventArrayList, event)); // ∫Q
                event.setHighestLevelOfQ(calculateHighestLevelOfQ(eventArrayList)); // Q*
                event.setAreaUnderServerBusy(calculateAreaUnderServerBusy(eventArrayList, event)); // ∫B
            }

            if(event.getTime() > numberOfMinutes){
                break;
            }
            eventArrayList.add(event);
            currentTime = event.getTime();
        }

    return eventArrayList;
    }


    public double getPrevWaitingTimeQueueSoFar(ArrayList<Event> eventArrayList) {
        return eventArrayList.get(eventArrayList.size()-1).getWaitingTimeInQueueSoFar();
    }

    public double getPrevSigmaTS(ArrayList<Event> eventArrayList) {
        return eventArrayList.get(eventArrayList.size()-1).getTotalTimeSpentInSystemByAllPartsThatHaveDeparted();
    }

    public double getPrevWQ(ArrayList<Event> eventArrayList) {
        return eventArrayList.get(eventArrayList.size()-1).getLongestTimeSpentInQueueSoFar();
    }

    public double getPrevTS(ArrayList<Event> eventArrayList) {
        return eventArrayList.get(eventArrayList.size()-1).getLongestTimeInSystem();
    }

    public double getPrevTime(ArrayList<Event> eventArrayList) {
        return eventArrayList.get(eventArrayList.size()-1).getTime();
    }

    public double getPrevAreaUnderCurve(ArrayList<Event> eventArrayList) {
        return  eventArrayList.get(eventArrayList.size()-1).getAreaUnderQueueLengthCurve();
    }

    public double getPrevNumOfPartsInQueue(ArrayList<Event> eventArrayList) {
        return  eventArrayList.get(eventArrayList.size()-1).getNumberOfPartsInQueue();
    }

    public double getPrevAreaUnderServerBusy(ArrayList<Event> eventArrayList) {
        return  eventArrayList.get(eventArrayList.size()-1).getAreaUnderServerBusy();
    }

    public double getPrevUtil(ArrayList<Event> eventArrayList) {
        return  eventArrayList.get(eventArrayList.size()-1).getUtilization();
    }

    public double roundValue(double value){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        String roundedValue = decimalFormat.format(value);
        return Double.parseDouble(roundedValue);
    }

    public ArrayList<Event> constructCalendar(ArrayList<Part> parts) {
//        ArrayList<Part> parts = new ArrayList<>();
//        Part partt = new Part(1,0,0,7.05);
//        Part part1 = new Part(2,9.07,9.07,11.37);
//        Part part2 = new Part(3,15.95,6.88,10.94);
//        Part part3 = new Part(4,29.28,13.33,9.49);
//        Part part4 = new Part(5, 13, 2,2);
//        Part part5 = new Part(6, 21, 8,2);
//        Part part6 = new Part(7, 29, 8,1);
//        Part part7 = new Part(8, 31, 6,4);
//        Part part8 = new Part(9, 31, 4,1);
//        parts.add(partt);
//        parts.add(part1);
//        parts.add(part2);
//        parts.add(part3);
//        parts.add(part4);
//        parts.add(part5);
//        parts.add(part6);
//        parts.add(part7);
//        parts.add(part8);

        ArrayList<Event> calendar = new ArrayList<>();
        Event eventInService = new Event();
        // construct a list of events consisting of arrival times
        // reminder, indices are 0-based
        ArrayList<Event> arrivalEvents = new ArrayList<>();
        for (Part part : parts) {
            Event event = new Event();
            event.setEventType(1);
            event.setTime(part.getArrivalTime());
            event.setEntityNumber(part.getId());
            arrivalEvents.add(event);
            calendar.add(event);
        }
        // construct a list of events consisting of departure times
        ArrayList<Event> departureEvents = new ArrayList<>();
        for (Part part : parts) {
            Event departure = new Event();
            // case part 1
            if (part.getId() == 1) {
                departure.setEventType(2);
                departure.setTime(part.getArrivalTime() + part.getServiceTime());
                departure.setEntityNumber(part.getId());
                eventInService = departure;
                departureEvents.add(departure);
                calendar.add(eventInService);
            } else {
                if(eventInService.getTime() <= part.getArrivalTime()){
                    departure.setTime(roundValue(part.getArrivalTime() + part.getServiceTime()));
                } else {
                    departure.setTime(roundValue(eventInService.getTime() + part.getServiceTime()));
                }
                departure.setEventType(2);

                departure.setEntityNumber(part.getId());
                eventInService = departure;
                departureEvents.add(departure);
                calendar.add(eventInService);
            }
        }
        // sort list by time
        calendar.sort(Comparator.comparing(Event::getTime));
        // calendar is the superlist, containing the entire calendar
        // get the time of the calendar entry that matches with
        // the index of the event list
        System.out.println("Parts");
        for(Event e: calendar){

            System.out.printf("%-30s%-30s%n",
                    e.getEntityNumber(), e.getTime());

        }
        System.out.println();
        for(Part parttt: parts) {
            System.out.printf("%-30s%-30s%-30s%-30s%n",
                    parttt.getId(), parttt.getArrivalTime(), parttt.getInterArrivalTime(), parttt.getServiceTime());
        }
        System.out.println();
        return calendar;
    }



    // removes queue if event is departure and adds queue if arrival
    // keep the index of the event id and queue same to its queue
    public ArrayList<Double> calculateTimesInQueue(ArrayList<Integer> eventIDsInQueue, ArrayList<Event> eventArrayList, Event presentEvent) {
        ArrayList<Double> timesInQueue = new ArrayList<>();
        for (int eventId: eventIDsInQueue){
            if (eventId == eventIDsInQueue.get(0))
                continue;
            for (Event event: eventArrayList) {
                if (eventId == event.getEventID()){
                    timesInQueue.add(event.getTime());
                }

            }
        }
        if (eventIDsInQueue.size() != 1 && presentEvent.getEventType() != 2)
            timesInQueue.add(presentEvent.getTime());
        Collections.reverse(timesInQueue);
        return timesInQueue;
    }

    // returns the service time of the event in front of the queue
    // retain value if event is arrival
    public double calculatePartInServiceTime(ArrayList<Integer> eventIDsInQueue, ArrayList<Event> eventArrayList) {
        double arrivalTime = 0;
        for (Event event: eventArrayList){
            if (!eventIDsInQueue.isEmpty()){
                if (eventIDsInQueue.get(0) == event.getEventID())
                    arrivalTime = event.getTime();
            }
        }

        return arrivalTime;
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
    public double calculateLongestTimeSpentInQueueSoFar(double partInServiceTime, double eventTime, int eventType, double prevWQ) {
        double longestTimeSpentInQueueSoFar = 0;
        if(eventType == 2){
            longestTimeSpentInQueueSoFar = eventTime - partInServiceTime;
            if (partInServiceTime == 0){
                longestTimeSpentInQueueSoFar = prevWQ;
            }
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
            String roundedValue = decimalFormat.format(longestTimeSpentInQueueSoFar);
            longestTimeSpentInQueueSoFar = Double.parseDouble(roundedValue);
        }
        if(eventType == 1){
            longestTimeSpentInQueueSoFar = prevWQ;
        }
        return longestTimeSpentInQueueSoFar;
    }

    // retain values f event is arrival
    // sigma WQ
    public double calculateWaitingTimeInQueueSoFar(int eventType, double prevWaitingTimeInQueueSoFar, double WQ,
                                                   double partInServiceTime) {
        double waitingTimeInQueueSoFar = 0;
        if (eventType == 2){
            waitingTimeInQueueSoFar = WQ + prevWaitingTimeInQueueSoFar;
            if(partInServiceTime == 0)
                waitingTimeInQueueSoFar = prevWaitingTimeInQueueSoFar;

            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
            String roundedValue = decimalFormat.format(waitingTimeInQueueSoFar);
            waitingTimeInQueueSoFar = Double.parseDouble(roundedValue);
        }
        if (eventType == 1){
            waitingTimeInQueueSoFar = prevWaitingTimeInQueueSoFar;
        }
        return waitingTimeInQueueSoFar;
    }

    // retain values if event type is arrival
    // TS*
    public double calculateLongestTimeInSystem(ArrayList<Event> eventArrayList, int eventType, int eventID,
                                               double prevTS, double eventTime) {
        double arrivalTime = 0;
        if (eventType == 2) {
            for (Event event : eventArrayList) {
                if (eventID == event.getEventID()) {
                    arrivalTime = event.getTime();
                }
            }
            return roundValue(eventTime - arrivalTime);
        }
        return prevTS;
    }

    // retain values if event is arrival
    // sigma TS
    public double calculateTotalTimeSpentInSystemByAllPartsThatHaveDeparted(double prevSigmaTs, double TS, int eventType) {
        if (eventType == 2){
           return roundValue(prevSigmaTs + TS);
        }
        return prevSigmaTs;
    }


    public double calculateAreaUnderQueueLengthCurve(ArrayList<Event> eventArrayList, Event event) {
        double currentTime = event.getTime();
        double prevTime = getPrevTime(eventArrayList);
        double prevNumOfPartsInQueue = getPrevNumOfPartsInQueue(eventArrayList);
        double prevAreaUnderCurve = getPrevAreaUnderCurve(eventArrayList);
        double areaUnderQueueLengthCurve = ((currentTime - prevTime) * (prevNumOfPartsInQueue - 0)) + prevAreaUnderCurve;
        return roundValue(areaUnderQueueLengthCurve);
    }

    public double calculateHighestLevelOfQ(ArrayList<Event> eventArrayList) {
        double highestLevelOfQ = 0;
        for (Event event : eventArrayList){
            if(highestLevelOfQ < event.getNumberOfPartsInQueue()){
                highestLevelOfQ = event.getNumberOfPartsInQueue();
            }
        }
        return highestLevelOfQ;
    }

    public double calculateAreaUnderServerBusy(ArrayList<Event> eventArrayList, Event event) {
        double currentTime = event.getTime();
        double prevTime = getPrevTime(eventArrayList);
        double prevUtil = getPrevUtil(eventArrayList);
        double prevAreaUnderServerBusy = getPrevAreaUnderServerBusy(eventArrayList);
        double areaUnderServerBusy = ((currentTime - prevTime) * (prevUtil - 0)) + prevAreaUnderServerBusy;
        return roundValue(areaUnderServerBusy);
    }

    /*
            FOR STATISTICS
     */
    public double averageTotalTimeInSystem(ArrayList<Event> eventArrayList) {
        Event e = eventArrayList.get(eventArrayList.size()-1);
        double totalTime = e.getTotalTimeSpentInSystemByAllPartsThatHaveDeparted();
        double totalPart = e.getPartsProducedSoFar();
        return totalTime/totalPart; // time per part
    }

    public double averageWaitingTimeInQueue(ArrayList<Event> eventArrayList) {
        Event e = eventArrayList.get(eventArrayList.size()-1);
        double totalTime = e.getWaitingTimeInQueueSoFar();
        double totalParts = e.getNumberOfPartsThatPassedThroughTheQueueSoFar();
        return totalTime/totalParts; // time per part
    }

    public double timeAverageNumberInQueue(ArrayList<Event> eventArrayList) {
        Event e = eventArrayList.get(eventArrayList.size()-1);
        double areaUnderQ = e.getAreaUnderQueueLengthCurve();
        double finalClockValue = e.getTime();
        return areaUnderQ/finalClockValue; // time per part
    }

    public double drillPressUtilization(ArrayList<Event> eventArrayList) {
        Event e = eventArrayList.get(eventArrayList.size()-1);
        double areaUnderB = e.getAreaUnderServerBusy();
        double finalClockValue = e.getTime();
        return areaUnderB/finalClockValue; // per minute
    }


    public Part getPrevPart(ArrayList<Part> parts) {
        return parts.get(parts.size() -1);
    }

    public ArrayList<Part> simulateParts (int minutes) {
        ArrayList<Part> parts = new ArrayList<>();
        Part part = new Part(1, 0, 0, 0);
        double simulationTime = 0;

        while(simulationTime < minutes) {
            // case first part in queue, arrival time is at 0
            if (parts.isEmpty()) {
                part.setServiceTime(Randomizer.lookUpServiceTime());
                parts.add(part);
            }
            // parts 2 and beyond
            else {
                // add the interarrival time of the new part
                // and the arrival time of the previous part
                double currentInterArrivalTime = Randomizer.lookupInterArrivalTime();
                parts.add(new Part(
                        getPrevPart(parts).getId() + 1,
                        roundValue(getPrevPart(parts).getArrivalTime() + currentInterArrivalTime),
                        currentInterArrivalTime,
                        Randomizer.lookUpServiceTime()

                ));
            }
            if (!parts.isEmpty()) {
                simulationTime = parts.get(parts.size() - 1).getArrivalTime() +
                        parts.get(parts.size() - 1).getServiceTime();
            }
        }
        return parts;
    }

}

// for testing
class Test {
    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        //simulator.simulateParts(40);
        ArrayList<Event> eventArrayList = simulator.simulateMinutes(30);
        //System.out.println( simulator.simulateParts(2));

        for(Event e: eventArrayList){
            System.out.printf("%-30s%-30s%-30s%-30s%-30s%-30s%-30s%-30s%-30s%-30s%-30s%-30s%-30s%-30s%-30s%-30s%n",
                    e.getEventID(), e.getTime(), e.getEventType(), e.getNumberOfPartsInQueue(),
                    e.getUtilization(), e.getTimesInQueue(), e.getPartInServiceTime(), e.getPartsProducedSoFar(),
                    e.getNumberOfPartsThatPassedThroughTheQueueSoFar(), e.getWaitingTimeInQueueSoFar(),
                    e.getLongestTimeSpentInQueueSoFar(), e.getTotalTimeSpentInSystemByAllPartsThatHaveDeparted(),
                    e.getLongestTimeInSystem(), e.getAreaUnderQueueLengthCurve(), e.getHighestLevelOfQ(),
                    e.getAreaUnderServerBusy());

        }
    }
}
