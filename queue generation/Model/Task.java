package Model;

public class Task {
    private static int lastId = 0;
    private int id;
    private int arrivalTime;
    private int serviceTime;
    private int serviceStartTime;
    public Task(int arrivalTime, int serviceTime) {
        lastId++;
        this.id = lastId;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(int serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }
    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }
}
