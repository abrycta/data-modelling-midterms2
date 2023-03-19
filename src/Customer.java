public class Customer {
    int customerID;
    int arrivalTime;
    int interarrivalTime;
    int serviceTime;

    public Customer() {
        this.customerID = 0;
        this.arrivalTime = 0;
        this.interarrivalTime = 0;
        this.serviceTime = 0;
    }

    public Customer(int customerID, int arrivalTime, int interarrivalTime, int serviceTime) {
        this.customerID = customerID;
        this.arrivalTime = arrivalTime;
        this.interarrivalTime = interarrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getInterarrivalTime() {
        return interarrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setInterarrivalTime(int interarrivalTime) {
        this.interarrivalTime = interarrivalTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }
}
