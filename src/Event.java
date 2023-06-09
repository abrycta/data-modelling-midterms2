import java.util.ArrayList;

public class Event {
    private int eventID;        // determines the sequence of events

    private int entityNumber;   // what part is the event referring to?
    private double time;
    private int eventType;
    private ArrayList<Double> timesInQueue;
    private int numberOfPartsInQueue;    // number of parts in queue at time t
    private int utilization; // 1 if the machine is busy at time t
                     // 0 if machine is available
    private double partArrivalTime; // attributes (arrival times)
    private double serviceTime;     // service time of part
    private double partInServiceTime; // In service attribute

    // statistical accumulators
    private int partsProducedSoFar; // p

    private int numberOfPartsThatPassedThroughTheQueueSoFar;    // n
    private double waitingTimeInQueueSoFar; // sigma wq
    private double longestTimeSpentInQueueSoFar;    // WQ*

    private double totalTimeSpentInSystemByAllPartsThatHaveDeparted;    // sigmaTS
    private double longestTimeInSystem; // TS*

    private double areaUnderQueueLengthCurve;   // integral Q

    private double highestLevelOfQ; // Q*, highest level that Q(t) has attained so far

    private double areaUnderServerBusy; // integral B

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public ArrayList<Double> getTimesInQueue() {
        return timesInQueue;
    }

    public void setTimesInQueue(ArrayList<Double> timesInQueue) {
        this.timesInQueue = timesInQueue;
    }

    public double getPartInServiceTime() {
        return partInServiceTime;
    }

    public void setPartInServiceTime(double partInServiceTime) {
        this.partInServiceTime = partInServiceTime;
    }

    public int getNumberOfPartsInQueue() {
        return numberOfPartsInQueue;
    }

    public void setNumberOfPartsInQueue(int numberOfPartsInQueue) {
        this.numberOfPartsInQueue = numberOfPartsInQueue;
    }

    public int getUtilization() {
        return utilization;
    }

    public void setUtilization(int utilization) {
        this.utilization = utilization;
    }

    public double getPartArrivalTime() {
        return partArrivalTime;
    }

    public void setPartArrivalTime(double partArrivalTime) {
        this.partArrivalTime = partArrivalTime;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getPartsProducedSoFar() {
        return partsProducedSoFar;
    }

    public void setPartsProducedSoFar(int partsProducedSoFar) {
        this.partsProducedSoFar = partsProducedSoFar;
    }

    public int getNumberOfPartsThatPassedThroughTheQueueSoFar() {
        return numberOfPartsThatPassedThroughTheQueueSoFar;
    }

    public void setNumberOfPartsThatPassedThroughTheQueueSoFar(int numberOfPartsThatPassedThroughTheQueueSoFar) {
        this.numberOfPartsThatPassedThroughTheQueueSoFar = numberOfPartsThatPassedThroughTheQueueSoFar;
    }

    public double getWaitingTimeInQueueSoFar() {
        return waitingTimeInQueueSoFar;
    }

    public void setWaitingTimeInQueueSoFar(double waitingTimeInQueueSoFar) {
        this.waitingTimeInQueueSoFar = waitingTimeInQueueSoFar;
    }

    public double getLongestTimeSpentInQueueSoFar() {
        return longestTimeSpentInQueueSoFar;
    }

    public void setLongestTimeSpentInQueueSoFar(double longestTimeSpentInQueueSoFar) {
        this.longestTimeSpentInQueueSoFar = longestTimeSpentInQueueSoFar;
    }

    public double getTotalTimeSpentInSystemByAllPartsThatHaveDeparted() {
        return totalTimeSpentInSystemByAllPartsThatHaveDeparted;
    }

    public void setTotalTimeSpentInSystemByAllPartsThatHaveDeparted(double totalTimeSpentInSystemByAllPartsThatHaveDeparted) {
        this.totalTimeSpentInSystemByAllPartsThatHaveDeparted = totalTimeSpentInSystemByAllPartsThatHaveDeparted;
    }

    public double getLongestTimeInSystem() {
        return longestTimeInSystem;
    }

    public void setLongestTimeInSystem(double longestTimeInSystem) {
        this.longestTimeInSystem = longestTimeInSystem;
    }

    public double getAreaUnderQueueLengthCurve() {
        return areaUnderQueueLengthCurve;
    }

    public void setAreaUnderQueueLengthCurve(double areaUnderQueueLengthCurve) {
        this.areaUnderQueueLengthCurve = areaUnderQueueLengthCurve;
    }

    public double getHighestLevelOfQ() {
        return highestLevelOfQ;
    }

    public void setHighestLevelOfQ(double highestLevelOfQ) {
        this.highestLevelOfQ = highestLevelOfQ;
    }

    public double getAreaUnderServerBusy() {
        return areaUnderServerBusy;
    }

    public void setAreaUnderServerBusy(double areaUnderServerBusy) {
        this.areaUnderServerBusy = areaUnderServerBusy;
    }

    public int getEntityNumber() {
        return entityNumber;
    }

    public void setEntityNumber(int entityNumber) {
        this.entityNumber = entityNumber;
    }
}
