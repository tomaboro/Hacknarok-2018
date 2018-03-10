package pl.hacknarok.positivedevs.runit.entity;

import java.sql.Date;

public class Event {
    public int id;
    public String name;
    public Date start_date;
    public String place;

    public Event(int id, String name, Date start_date, String place) {
        this.id = id;
        this.name = name;
        this.start_date = start_date;
        this.place = place;
    }
}
