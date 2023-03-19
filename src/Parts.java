public class Parts {
    int entityNum;
    int time;
    String eventType;
    int variableQ;
    int variableB;
    int arrivalTimeInQueue;
    int arrivalTimeInService;

    public Parts() {
        this.entityNum = 0;
        this.time = 0;
        this.eventType = "";
        this.variableQ = 0;
        this.variableB = 0;
        this.arrivalTimeInQueue = 0;
        this.arrivalTimeInService = 0;
    }

    public Parts(int entityNum, int time, String eventType, int variableQ, int variableB, int arrivalTimeInQueue, int arrivalTimeInService) {
        this.entityNum = entityNum;
        this.time = time;
        this.eventType = eventType;
        this.variableQ = variableQ;
        this.variableB = variableB;
        this.arrivalTimeInQueue = arrivalTimeInQueue;
        this.arrivalTimeInService = arrivalTimeInService;
    }

    public int getEntityNum() {
        return entityNum;
    }

    public int getTime() {
        return time;
    }

    public String getEventType() {
        return eventType;
    }

    public int getVariableQ() {
        return variableQ;
    }

    public int getVariableB() {
        return variableB;
    }

    public int getArrivalTimeInQueue() {
        return arrivalTimeInQueue;
    }

    public int getArrivalTimeInService() {
        return arrivalTimeInService;
    }

    public void setEntityNum(int entityNum) {
        this.entityNum = entityNum;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setVariableQ(int variableQ) {
        this.variableQ = variableQ;
    }

    public void setVariableB(int variableB) {
        this.variableB = variableB;
    }

    public void setArrivalTimeInQueue(int arrivalTimeInQueue) {
        this.arrivalTimeInQueue = arrivalTimeInQueue;
    }

    public void setArrivalTimeInService(int arrivalTimeInService) {
        this.arrivalTimeInService = arrivalTimeInService;
    }
}
