package pl.hacknarok.positivedevs.runit.entity;

public class Logo {
    public int id;
    public int eventID;
    public String URL;

    public Logo(){};
    public Logo(int id, int eventID, String URL) {
        this.id = id;
        this.eventID = eventID;
        this.URL = URL;
    }
}
