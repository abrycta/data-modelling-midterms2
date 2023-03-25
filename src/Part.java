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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getInterArrivalTime() {
        return interArrivalTime;
    }

    public void setInterArrivalTime(double interArrivalTime) {
        this.interArrivalTime = interArrivalTime;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;
    }
}