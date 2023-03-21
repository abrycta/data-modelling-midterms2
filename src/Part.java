public class Part {
    private int id;
    private double arrivalTime;
    private double interArrivalTime;
    private double serviceTime;

    public Part(int id, double arrivalTime, double interArrivalTime, double serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.interArrivalTime = interArrivalTime;
        this.serviceTime = serviceTime;
    }
}