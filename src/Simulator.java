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
        while (counter < partsCalendar.size()+1){
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

                event.setAreaUnderQueueLengthCurve(calculateAreaUnderQueueLengthCurve(event.getTime(), getPrevTime(eventArrayList), event.getNumberOfPartsInQueue()-1, getPrevAreaUnderCurve(eventArrayList))); // ∫Q
                event.setHighestLevelOfQ(calculateHighestLevelOfQ(eventArrayList)); // Q*
                event.setAreaUnderServerBusy(calculateAreaUnderServerBusy(event.getTime(), getPrevTime(eventArrayList), event.getUtilization()-1, getPrevAreaUnderServerBusy(eventArrayList))); // ∫B
            }
            if (counter == partsCalendar.size()){

            }
            eventArrayList.add(event);
            counter++;
        }
    return eventArrayList;
    }

    // returns prev event times in queue
    public ArrayList<Double> getPrevTimesInQueue(ArrayList<Event> eventArrayList) {
        return eventArrayList.get(eventArrayList.size()-1).getTimesInQueue();
    }

    // returns prev part in service time
    public double getPrevPartInServiceTime(ArrayList<Event> eventArrayList) {
        return eventArrayList.get(eventArrayList.size()-1).getPartInServiceTime();
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

    public double getPrevAreaUnderServerBusy(ArrayList<Event> eventArrayList) {
        return  eventArrayList.get(eventArrayList.size()-1).getAreaUnderServerBusy();
    }

    public ArrayList<Event> constructCalendar(ArrayList<Part> parts1) {

        ArrayList<Event> calendar = new ArrayList<>();
        Event eventInService = new Event();
        ArrayList<Event> arrivalEvents = new ArrayList<>();

        Event arrivalEvent = new Event();
        Event departure = new Event();
        Queue<Event> arrivalEventsQueue = new LinkedList<>();
        Event lastDeparture = new Event();

        // for testing only
        ArrayList<Part> parts = new ArrayList<>();
        Part part = new Part(1,0,0,4);
        Part part1 = new Part(2,3,3,3);
        Part part2 = new Part(3,5,2,2);
        Part part3 = new Part(4,10,5,3);
        parts.add(part);
        parts.add(part1);
        parts.add(part2);
        parts.add(part3);
        //

        int partsCounter = 0;
        int counter = 0;
        while (counter < parts.size()*2) {
            arrivalEvent = new Event();
            departure = new Event();
            if (partsCounter == 0){
                arrivalEvent.setEventType(1);
                arrivalEvent.setTime(parts.get(partsCounter).getArrivalTime());
                arrivalEvent.setEntityNumber(parts.get(partsCounter).getId());
                arrivalEvent.setServiceTime(parts.get(partsCounter).getServiceTime());
                arrivalEvents.add(arrivalEvent);
                eventInService = arrivalEvent;
                partsCounter++;
                calendar.add(arrivalEvent);
            } else if (parts.size()-1 < partsCounter) {
                departure.setEventType(2);
                departure.setTime(eventInService.getTime() + eventInService.getServiceTime());
                departure.setEntityNumber(eventInService.getEntityNumber());
                lastDeparture = departure;
                calendar.add(departure);
                eventInService = arrivalEventsQueue.remove();
                if (arrivalEventsQueue.isEmpty()) {
                    eventInService = new Event();
                }
            } else if (eventInService.getServiceTime() == 0){
                arrivalEvent.setEventType(1);
                arrivalEvent.setTime(parts.get(partsCounter).getArrivalTime());
                arrivalEvent.setEntityNumber(parts.get(partsCounter).getId());
                arrivalEvent.setServiceTime(parts.get(partsCounter).getServiceTime());
                arrivalEvents.add(arrivalEvent);
                arrivalEventsQueue.add(arrivalEvent);
                eventInService = arrivalEvent;
                partsCounter++;
                calendar.add(arrivalEvent);
            }  else if (lastDeparture.getTime() + eventInService.getServiceTime() > parts.get(partsCounter).getArrivalTime()) {
                arrivalEvent.setEventType(1);
                arrivalEvent.setTime(parts.get(partsCounter).getArrivalTime());
                arrivalEvent.setEntityNumber(parts.get(partsCounter).getId());
                arrivalEvent.setServiceTime(parts.get(partsCounter).getServiceTime());
                arrivalEvents.add(arrivalEvent);
                arrivalEventsQueue.add(arrivalEvent);
                partsCounter++;
                calendar.add(arrivalEvent);
            } else if (lastDeparture.getTime() + eventInService.getServiceTime() <= parts.get(partsCounter).getArrivalTime()) {
                departure.setEventType(2);
                if (lastDeparture.getTime() < eventInService.getTime() && lastDeparture.getTime() != 0){
                    departure.setTime(eventInService.getTime() + eventInService.getServiceTime());
                } else {
                    departure.setTime(lastDeparture.getTime() + eventInService.getServiceTime());
                }

                departure.setEntityNumber(eventInService.getEntityNumber());
                lastDeparture = departure;
                calendar.add(departure);
                if (arrivalEventsQueue.isEmpty()) {
                    eventInService = new Event();
                } else {
                    eventInService = arrivalEventsQueue.remove();
                }
            }
            counter++;


        }

        // calendar is the superlist, containing the entire calendar
        // get the time of the calendar entry that matches with
        // the index of the event list
        return calendar;
    }


    // removes queue if event is departure and adds queue if arrival
    // keep the index of the event id and queue same to its queue
    // subtract 1 to the total events in Queue (will return empty arraylist if eventIDs in Queue is 1)
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
        timesInQueue.add(presentEvent.getTime());
        }
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
            return eventTime - arrivalTime;
        }
        return prevTS;
    }

    // retain values if event is arrival
    // sigma TS
    public double calculateTotalTimeSpentInSystemByAllPartsThatHaveDeparted(double prevSigmaTs, double TS, int eventType) {
        if (eventType == 2){
           return prevSigmaTs + TS;
        }
        return prevSigmaTs;
    }


    public double calculateAreaUnderQueueLengthCurve(double eventTime, double prevTime, double Q, double prevAreaUnderCurve) {
        double areaUnderQueueLengthCurve = (eventTime - prevTime) * (Q - 0) + prevAreaUnderCurve;
        return areaUnderQueueLengthCurve;
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

    public double calculateAreaUnderServerBusy(double eventTime, double prevTime, double B, double prevAreaUnderServerBusy) {
        double areaUnderServerBusy = (eventTime - prevTime) * (B - 0) + prevAreaUnderServerBusy;
        return areaUnderServerBusy;
    }

    public Part getPrevPart(ArrayList<Part> parts) {
        return parts.get(parts.size() -1);
    }

    public Event getPrevEvent(ArrayList<Event> events) {
        return events.get(events.size() - 1);
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
                int currentInterArrivalTime = Randomizer.lookupInterArrivalTime();
                parts.add(new Part(
                        getPrevPart(parts).getId() + 1,
                        getPrevPart(parts).getArrivalTime() + currentInterArrivalTime,
                        currentInterArrivalTime,
                        Randomizer.lookUpServiceTime()

                ));
            }
            if (!parts.isEmpty()) {
                simulationTime = parts.get(parts.size() - 1).getArrivalTime();
            }
        }
        return parts;
    }

}


class Test {
    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        simulator.simulateParts(40);
        ArrayList<Event> eventArrayList = simulator.simulateMinutes(10);
       System.out.println( simulator.simulateParts(2));

        for(Event e: eventArrayList){
//            System.out.print(e.getEventID() + "    ");
//            System.out.print(e.getTime() + "    ");
//            System.out.print(e.getEventType() + "    ");
//            System.out.print(e.getTimesInQueue() + "    ");
//            System.out.print(e.getNumberOfPartsInQueue() + "    ");
//            System.out.print(e.getUtilization() + "    ");
//            System.out.print(e.getPartArrivalTime() + "    ");
//            System.out.print(e.getPartInServiceTime() + "    ");
//            System.out.print(e.getPartsProducedSoFar() + "    ");
//            System.out.print(e.getNumberOfPartsThatPassedThroughTheQueueSoFar() + "    ");
//            System.out.print(e.getWaitingTimeInQueueSoFar() + "    ");
//            System.out.print(e.getLongestTimeSpentInQueueSoFar() + "    ");
//            System.out.print(e.getTotalTimeSpentInSystemByAllPartsThatHaveDeparted() + "    ");
//            System.out.print(e.getLongestTimeInSystem() + "    ");
//            System.out.print(e.getAreaUnderQueueLengthCurve() + "    ");
//            System.out.print(e.getHighestLevelOfQ() + "    ");
//            System.out.print(e.getAreaUnderServerBusy() + "    ");
//            System.out.println();
            System.out.printf("%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%n",
                    e.getEventID(), e.getTime(), e.getEventType(), e.getNumberOfPartsInQueue(),
                    e.getUtilization(), e.getTimesInQueue(), e.getPartInServiceTime(), e.getPartsProducedSoFar(),
                    e.getNumberOfPartsThatPassedThroughTheQueueSoFar(), e.getWaitingTimeInQueueSoFar(),
                    e.getLongestTimeSpentInQueueSoFar(), e.getTotalTimeSpentInSystemByAllPartsThatHaveDeparted(),
                    e.getLongestTimeInSystem(), e.getAreaUnderQueueLengthCurve(), e.getHighestLevelOfQ(),
                    e.getAreaUnderServerBusy());

        }
    }
}
