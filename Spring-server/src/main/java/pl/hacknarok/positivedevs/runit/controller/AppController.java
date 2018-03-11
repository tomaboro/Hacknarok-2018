package pl.hacknarok.positivedevs.runit.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.hacknarok.positivedevs.runit.MotorolaJSONFactory;
import pl.hacknarok.positivedevs.runit.entity.Event;
import pl.hacknarok.positivedevs.runit.entity.Logo;
import pl.hacknarok.positivedevs.runit.entity.Notification;
import pl.hacknarok.positivedevs.runit.repository.EventRepository;

import java.util.List;

@RestController
@RequestMapping("app")
public class AppController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "addaccident", method = RequestMethod.PUT)
    public void addAccident(@RequestBody Notification notification) throws JSONException {
        final String uri = "https://hacknarock.release.commandcentral.com";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Content-Type","application/json");
        headers.add("Authorization","Basic MFlrY2htcllOM2NxMXpM");

        String jsonToSend = MotorolaJSONFactory.createAccidentJSON(notification.id,notification.latitude,notification.longitude);
        HttpEntity<String> entity = new HttpEntity<>(jsonToSend, headers);

        System.out.println(jsonToSend);
        restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
    }

    @RequestMapping(value = "addlost", method = RequestMethod.PUT)
    public void addLost(@RequestBody Notification notification) throws JSONException {
        final String uri = "https://hacknarock.release.commandcentral.com";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Content-Type","application/json");
        headers.add("Authorization","Basic MFlrY2htcllOM2NxMXpM");

        String jsonToSend = MotorolaJSONFactory.createLostJSON(notification.id,notification.latitude,notification.longitude);
        HttpEntity<String> entity = new HttpEntity<>(jsonToSend, headers);

        System.out.println(jsonToSend);
        restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
    }
}