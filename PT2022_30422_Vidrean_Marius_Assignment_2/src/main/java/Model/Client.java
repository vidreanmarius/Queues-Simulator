package Model;

public class Client {

    private Integer clientId;
    private Integer arrivalTime;
    private Integer serviceTime;
    private Integer waitingTime = 0;

    public Client(Integer clientId, Integer arrivalTime, Integer serviceTime) {
        this.clientId = clientId;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void decreaseServiceTime(){
        this.serviceTime = this.serviceTime - 1;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Integer serviceTime) {
        this.serviceTime = serviceTime;
    }

    public Integer getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Integer waitingTime) {
        this.waitingTime = waitingTime;
    }

    @Override
    public String toString() {
        return " (" + clientId +" " + arrivalTime +" "+ serviceTime+") " ;
    }
}
