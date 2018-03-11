package pl.hacknarok.positivedevs.runit.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Event {
    public int id;
    public String name;
    public String description;
    public Timestamp start_date;
    public String place;

    public double latitude;
    public double longitude;

    public Event(){};

    public Event(int id, String name, String description,Timestamp start_date, String place) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.place = place;
    }
}
