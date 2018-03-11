package pl.hacknarok.positivedevs.runit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;

public class MotorolaJSONFactory {

    public static String createImageJSON(String id, double latitude, double longitude, String imageUrl) throws JSONException {

        JSONObject json = new JSONObject();
        json.put("metaHeader", createMetaHeader("image"));
        json.put("eventHeader", createEventHeaderImage(id, latitude, longitude, imageUrl));
        return json.toString();
    }

    public static String createAccidentJSON(String id, double latitude, double longitude) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("metaHeader", createMetaHeader("accident"));
        json.put("eventHeader", createEventHeaderAccident(id, latitude, longitude));
        return json.toString();
    }

    public static String createLostJSON(String id, double latitude, double longitude) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("metaHeader", createMetaHeader("lost"));
        json.put("eventHeader", createEventHeaderLost(id, latitude, longitude));
        return json.toString();
    }

    public static String createUserJSON(String id, double latitude, double longitude) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("metaHeader", createMetaHeader("runner"));
        json.put("eventHeader", createEventHeaderUser(id, latitude, longitude));
        return json.toString();
    }

    private static JSONObject createMetaHeader(String type) throws JSONException {
        JSONObject header = new JSONObject();
        Date date = new Date(System.currentTimeMillis());
        Instant instant = date.toInstant();
        LocalDateTime ldt = instant.atOffset(ZoneOffset.UTC).toLocalDateTime();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        header.put("metaTimeStamp", ldt.format(fmt) + "Z");
        header.put("metaEventTypeLabel", type);
        return header;
    }

    private static JSONObject createEventHeaderImage(String id, double latitude, double longitude, String imageUrl) throws JSONException {
        JSONObject header = new JSONObject();
        header.put("id", id);
        header.put("label", "image");
        Date date = new Date(System.currentTimeMillis());
        Instant instant = date.toInstant();
        LocalDateTime ldt = instant.atOffset(ZoneOffset.UTC).toLocalDateTime();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        header.put("timeStamp", ldt.format(fmt) + "Z");

        JSONObject location = new JSONObject();
        location.put("latitude", latitude);
        location.put("longitude", longitude);
        header.put("location", location);

        header.put("detailedDescription", "Image sent from RunIT App");

        JSONObject icon = new JSONObject();
        icon.put("url", "MsiIcon://ic_camera_still");
        header.put("icon", icon);

        date = new Date(System.currentTimeMillis() + 1000*60*30);
        instant = date.toInstant();
        ldt = instant.atOffset(ZoneOffset.UTC).toLocalDateTime();
        fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        header.put("expirationTimeStamp", ldt.format(fmt) + "Z");

        header.put("priority", "medium");

        JSONArray attachments = new JSONArray();
        JSONObject singleAttachment = new JSONObject();
        singleAttachment.put("name", id);
        singleAttachment.put("contentType", "img/png");
        singleAttachment.put("url", imageUrl);
        attachments.put(singleAttachment);
        header.put("attachments", attachments);

        return header;

    }

    private static JSONObject createEventHeaderAccident(String id, double latitude, double longitude) throws JSONException {
        JSONObject header = new JSONObject();
        header.put("id", id);
        header.put("label", "accident");
        Date date = new Date(System.currentTimeMillis());
        Instant instant = date.toInstant();
        LocalDateTime ldt = instant.atOffset(ZoneOffset.UTC).toLocalDateTime();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        header.put("timeStamp", ldt.format(fmt) + "Z");

        JSONObject location = new JSONObject();
        location.put("latitude", latitude);
        location.put("longitude", longitude);
        header.put("location", location);

        header.put("detailedDescription", "Accident reported by RunIT app");

        JSONObject icon = new JSONObject();
        icon.put("url", "MsiIcon://ic_event_alert_high");
        header.put("icon", icon);

        date = new Date(System.currentTimeMillis() + 1000*60*30);
        instant = date.toInstant();
        ldt = instant.atOffset(ZoneOffset.UTC).toLocalDateTime();
        fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        header.put("expirationTimeStamp", ldt.format(fmt) + "Z");

        header.put("priority", "high");
        return header;
    }

    private static JSONObject createEventHeaderLost(String id, double latitude, double longitude) throws JSONException {
        JSONObject header = new JSONObject();
        header.put("id", id);
        header.put("label", "lost");
        Date date = new Date(System.currentTimeMillis());
        Instant instant = date.toInstant();
        LocalDateTime ldt = instant.atOffset(ZoneOffset.UTC).toLocalDateTime();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        header.put("timeStamp", ldt.format(fmt) + "Z");

        JSONObject location = new JSONObject();
        location.put("latitude", latitude);
        location.put("longitude", longitude);
        header.put("location", location);

        header.put("detailedDescription", "Lost reported by RunIT app");

        JSONObject icon = new JSONObject();
        icon.put("url", "MsiIcon://ic_incident_pending");
        header.put("icon", icon);

        date = new Date(System.currentTimeMillis() + 1000*60*30);
        instant = date.toInstant();
        ldt = instant.atOffset(ZoneOffset.UTC).toLocalDateTime();
        fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        header.put("expirationTimeStamp", ldt.format(fmt) + "Z");

        header.put("priority", "high");
        return header;
    }

    private static JSONObject createEventHeaderUser(String id, double latitude, double longitude) throws JSONException {
        JSONObject header = new JSONObject();
        header.put("id", id);
        header.put("label", "runner");
        Date date = new Date(System.currentTimeMillis());
        Instant instant = date.toInstant();
        LocalDateTime ldt = instant.atOffset(ZoneOffset.UTC).toLocalDateTime();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        header.put("timeStamp", ldt.format(fmt) + "Z");

        JSONObject location = new JSONObject();
        location.put("latitude", latitude);
        location.put("longitude", longitude);
        header.put("location", location);

        header.put("detailedDescription", "Image sent from RunIT App");

        JSONObject icon = new JSONObject();
        icon.put("url", "MsiIcon://ic_action_onfoot");
        header.put("icon", icon);

        date = new Date(System.currentTimeMillis() + 1000*60*30);
        instant = date.toInstant();
        ldt = instant.atOffset(ZoneOffset.UTC).toLocalDateTime();
        fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        header.put("expirationTimeStamp", ldt.format(fmt) + "Z");

        header.put("priority", "low");
        return header;

    }
}
