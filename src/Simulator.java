import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        ArrayList<Part> parts = simulateParts(numberOfMinutes);

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
                event.setEventID(calculateEventID(parts, eventArrayList));

                event.setTime(calculateTime(parts, eventArrayList));

                event.setEventType(getEventType(parts, eventArrayList)); // 0 = Initialize, 1 = Arrival, 2 = Departure

                // Stores event IDs that are in queue
                if (event.getEventType() == 1) {
                    eventIDsInQueue.add(event.getEventID());
                    if(event.getEventID() == 1){
                        eventIdsPassedQueue.add(event.getEventID());
                    }
                } else if (event.getEventType() == 2){
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

                event.setPartInServiceTime(calculatePartInServiceTime(eventIDsInQueue, eventArrayList));

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

                event.setAreaUnderQueueLengthCurve(calculateAreaUnderQueueLengthCurve(event.getTime(), getPrevTime(eventArrayList), event.getNumberOfPartsInQueue()-1, getPrevAreaUnderCurve(eventArrayList))); // ∫Q
                event.setHighestLevelOfQ(calculateHighestLevelOfQ(eventArrayList)); // Q*
                event.setAreaUnderServerBusy(calculateAreaUnderServerBusy(event.getTime(), getPrevTime(eventArrayList), event.getUtilization()-1, getPrevAreaUnderServerBusy(eventArrayList))); // ∫B
            }
            eventArrayList.add(event);
            // currentTime = next arrival time ** PAKIAYOS
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

    // Returns the eventID
    public int calculateEventID(ArrayList<Part> parts, ArrayList<Event> events) {
        // set time of arrival event of entity 1 to 0
        return constructCalendar(parts, events).get(events.size()-1).getEntityNumber();
    }

    public double calculateTime(ArrayList<Part> parts, ArrayList<Event> events) {
        // set time of arrival event of entity 1 to 0
        if (getPrevEvent(events).getEventType() == 0) return 0;
        return constructCalendar(parts, events).get(events.size()-1).getTime();
    }

    public int getEventType(ArrayList<Part> parts, ArrayList<Event> events) {
        if (getPrevEvent(events).getEventType() == 0) return 1;
        return constructCalendar(parts, events).get(events.size()-1).getEventType();
    }


    //
    public ArrayList<Event> constructCalendar(ArrayList<Part> parts, ArrayList<Event> events) {

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
                departure.setTime(part.getServiceTime());
                eventInService = departure;
                departureEvents.add(departure);
                calendar.add(eventInService);
                // systemTime += part.getServiceTime();
            } else {
                departure.setEventType(2);
                departure.setTime(
                        eventInService.getTime() + part.getServiceTime()
                );
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
        return calendar;
    }


    // removes queue if event is departure and adds queue if arrival
    // keep the index of the event id and queue same to its queue
    // subtract 1 to the total events in Queue (will return empty arraylist if eventIDs in Queue is 1)
    public ArrayList<Double> calculateTimesInQueue(ArrayList<Integer> eventIDsInQueue, ArrayList<Event> eventArrayList) {
        ArrayList<Double> timesInQueue = new ArrayList<>();
        for (int eventId: eventIDsInQueue){
            if (eventId == eventIDsInQueue.get(0))
                continue;
            for (Event event: eventArrayList) {
                timesInQueue.add(event.getPartArrivalTime());
            }
        }
        Collections.reverse(timesInQueue);
        return timesInQueue;
    }

    // returns the service time of the event in front of the queue
    // retain value if event is arrival
    public double calculatePartInServiceTime(ArrayList<Integer> eventIDsInQueue, ArrayList<Event> eventArrayList) {
        double serviceTime = 0;
        for (int eventId: eventIDsInQueue){
            if (eventId == eventIDsInQueue.get(0)) {
                continue;
            }
            for (Event event: eventArrayList){
                serviceTime = event.getPartInServiceTime();
            }
        }
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
    public double calculateLongestTimeSpentInQueueSoFar(double partInServiceTime, double eventTime, int eventType, double prevWQ) {
        double longestTimeSpentInQueueSoFar = 0;
        if(eventType == 2){
            longestTimeSpentInQueueSoFar = eventTime - partInServiceTime;
        }
        if(eventType == 1){
            longestTimeSpentInQueueSoFar = prevWQ;
        }
        return longestTimeSpentInQueueSoFar;
    }

    // retain values f event is arrival
    // sigma WQ
    public double calculateWaitingTimeInQueueSoFar(int eventType, double prevWaitingTimeInQueueSoFar, double WQ) {
        double waitingTimeInQueueSoFar = 0;
        if (eventType == 2){
            waitingTimeInQueueSoFar = WQ + prevWaitingTimeInQueueSoFar;
        }
        if (eventType == 1){
            waitingTimeInQueueSoFar = prevWaitingTimeInQueueSoFar;
        }
        return waitingTimeInQueueSoFar;
    }

    // retain values if event type is arrival
    // TS*
    public double calculateLongestTimeInSystem(int eventType, int eventID, ArrayList<Event> eventArrayList, double prevTS) {
        double longestTimeInSystem = 0;
        return longestTimeInSystem;
    }

    // retain values if event is arrival
    // sigma TS
    public double calculateTotalTimeSpentInSystemByAllPartsThatHaveDeparted(int eventType, int eventID, double prevSigmaTS, double TS) {
        double totalTimeSpentInSystemByAllPartsThatHaveDeparted = 0;
        return totalTimeSpentInSystemByAllPartsThatHaveDeparted;
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
            if (part.getId() == 1) {
                part.setServiceTime(Randomizer.lookUpServiceTime());
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
            simulationTime = parts.get(parts.size() - 1).getArrivalTime();
        }
        return parts;
    }




}
