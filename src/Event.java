public class Event {
    int eventID;
    double time;
    int eventType;
    double timeInQueue;
    double numberOfPartsInQueue;    // number of parts in queue at time t
    int utilization; // 1 if the machine is busy at time t
                     // 0 if machine is available
    double partArrivalTime; // attributes (arrival times)
    double serviceTime;     // service time of part

    // statistical accumulators
    int partsProducedSoFar; // p

    int numberOfPartsThatPassedThroughTheQueueSoFar;    // n
    double waitingTimeInQueueSoFar; // sigma wq
    double longestTimeSpentInQueueSoFar;    // WQ*

    double totalTimeSpentInSystemByAllPartsThatHaveDeparted;    // sigma TS
    double longestTimeInSystem; // TS*

    double areaUnderQueueLengthCurve;   // integral Q

    double highestLevelOfQ; // Q*, highest level that Q(t) has attained so far

    double areaUnderServerBusy; // integral B


}
