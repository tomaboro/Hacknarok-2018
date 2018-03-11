package pl.hacknarok.positivedevs.runit.repository;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import pl.hacknarok.positivedevs.runit.MotorolaJSONFactory;
import pl.hacknarok.positivedevs.runit.entity.Event;
import pl.hacknarok.positivedevs.runit.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected JdbcTemplate jdbc;

    public User getUser(long id) {
        return jdbc.queryForObject("SELECT * FROM sb_user WHERE id=?", userMapper, id);
    }

    public List<User> getUsers(long[] ids) {
        String inIds = StringUtils.arrayToCommaDelimitedString(ObjectUtils.toObjectArray(ids));
        return jdbc.query("SELECT * FROM sb_user WHERE id IN (" + inIds + ")", userMapper);
    }

    private static final RowMapper<User> userMapper = new RowMapper<User>() {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(rs.getInt("ID"), rs.getString("name"), rs.getString("password"));
            return user;
        }

    };

    private static final RowMapper<Event> eventMapper = new RowMapper<Event>() {
        public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
            Event event = new Event(rs.getInt("ID"), rs.getString("name"), rs.getString("description"), rs.getTimestamp("start_date"), rs.getString("place"));
            return event;
        }

    };

    public List<Event> getAllEvents() {
        return jdbc.query("SELECT * FROM Event", eventMapper);
    }

    public void createTable() {
        jdbc.execute(
                "CREATE TABLE Users(id int)");
        return;
    }

    public boolean checkIfUserExists(String name) {
        String statement = "SELECT * FROM Users WHERE name='" + name + "'";
        //List<User> userList =  jdbc.query("SELECT * FROM Users");
        List<User> tmp = jdbc.query(statement, userMapper);
        if (tmp.isEmpty())
            return false;
        return true;
    }

    public void addUser(String name, String password) {
        String statement = "INSERT INTO Users(name,password) VALUES('" + name + "','" + password + "')";
        jdbc.execute(statement);
        return;
    }

    public String login(String name, String password) {
        String statement = "SELECT * FROM Users WHERE name='" + name + "' AND password='" + password + "'";
        List<User> tmp = jdbc.query(statement, userMapper);
        if (tmp.isEmpty())
            return null;
        else {
            String uuid = UUID.randomUUID().toString();
            statement = "UPDATE Users SET token='" + uuid + "' WHERE name='" + name + "'";
            jdbc.execute(statement);
            return uuid;
        }
    }

    public void logout(String token) {
        String statement = "UPDATE Users SET token='' WHERE token='" + token + "'";
        jdbc.execute(statement);
    }

    public void updatePosition(User user) throws JSONException {
        String statement = "UPDATE Users SET longitude=" + user.longitude + ",latitude= " + user.latitude + " WHERE token='" + user.token + "'";
        jdbc.execute(statement);

        final String uri = "https://hacknarock.release.commandcentral.com";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Basic MFlrY2htcllOM2NxMXpM");

        String userName = jdbc.queryForObject("SELECT name FROM Users WHERE token='" + user.token + "'", String.class);

        String jsonToSend = MotorolaJSONFactory.createUserJSON(userName, user.latitude, user.longitude);
        HttpEntity<String> entity = new HttpEntity<>(jsonToSend, headers);

        restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);


        final String uri = "";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        //headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String jsonToSend = "{\"name\":\""+user.token+"\",\"longitude\":"+user.longitude+",\"latitude\":"+user.latitude+"}";
        HttpEntity<String> entity = new HttpEntity<>(jsonToSend, headers);

        restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

}