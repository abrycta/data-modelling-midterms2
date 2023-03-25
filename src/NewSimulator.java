import java.util.ArrayList;

public class NewSimulator {
    public Event getPrevEvent(ArrayList<Event> events) {
        return events.get(events.size() - 1);
    }
    public double calculateTime(ArrayList<Part> parts, ArrayList<Event> events) {

        ArrayList<Event> eventsInQueue = new ArrayList<>();
        Event eventInService = new Event();

        // set time of arrival event of entity 1 to 0
        if (getPrevEvent(events).getEventType() == 0) return 0;

        // construct a list of events consisting of arrival times
        // reminder, indices are 0-based
        ArrayList<Event> arrivalEvents = new ArrayList<>();
        for (Part part : parts) {
            Event event = new Event();
            event.setTime(part.getArrivalTime());
            event.setEntityNumber(part.getId());
            arrivalEvents.add(event);
        }

        // construct a list of events consisting of departure times
        ArrayList<Event> departureEvents = new ArrayList<>();
        for (Part part : parts) {
            Event departure = new Event();

            // case part 1
            if (part.getId() == 1) {
                departure.setTime(part.getServiceTime());
                eventInService = departure;
                departureEvents.add(departure);
                // systemTime += part.getServiceTime();
            } else {
                departure.setTime(
                        eventInService.getTime() + part.getServiceTime()
                );
                eventInService = departure;
                departureEvents.add(departure);
            }
        }



        // cycle through current prevEvent
        for (Event prevEvent : events) {
            Event currentEvent = new Event();
            // after initialization event
            if (prevEvent.getEventType() == 0) return parts.get(0).getArrivalTime();

                // if previous events are events 1 or more
            else {

            }


        }

    }
}
