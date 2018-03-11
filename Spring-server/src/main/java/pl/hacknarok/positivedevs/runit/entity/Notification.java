package pl.hacknarok.positivedevs.runit.entity;

public class Notification {
    public String id;
    public double longitude;
    public double latitude;

    public Notification(){}
    public Notification(String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
