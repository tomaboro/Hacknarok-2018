package pl.hacknarok.positivedevs.runit.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Event {
    public int id;
    public String name;
    public Timestamp start_date;
    public String place;

    public double longitude;
    public double latitude;

    public Event(){};

    public Event(int id, String name, Timestamp start_date, String place) {
        this.id = id;
        this.name = name;
        this.start_date = start_date;
        this.place = place;
    }
}
