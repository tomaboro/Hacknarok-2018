package pl.hacknarok.positivedevs.runit.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import pl.hacknarok.positivedevs.runit.entity.Event;
import pl.hacknarok.positivedevs.runit.entity.Logo;
import pl.hacknarok.positivedevs.runit.entity.User;
import pl.hacknarok.positivedevs.runit.repository.EventRepository;

import java.util.List;

@RestController
@RequestMapping("event")
public class EventController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private EventRepository events;

    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public String getAllEvents() throws JSONException {
        List<Event> eventList = events.getAllEvents();
        JSONObject json = new JSONObject();
        JSONArray arr = new JSONArray();
        for(Event event : eventList){
            JSONObject tmp = new JSONObject();
            tmp.put("id",event.id);
            tmp.put("name",event.name);
            tmp.put("place",event.place);
            tmp.put("start_date",event.start_date);
            arr.put(tmp);
        }
        json.put("events",arr);
        return json.toString();
    }

    @RequestMapping(value = "getLogoURL", method = RequestMethod.POST)
    public String getLogoURL(@RequestBody Logo logo) {
        return events.getLogoURL(logo.id);
    }

    @RequestMapping(value = "addEvent", method = RequestMethod.POST)
    public String getLogoURL(@RequestBody Event event) {
        events.addEvent(event);
        return "Event added";
    }
}

