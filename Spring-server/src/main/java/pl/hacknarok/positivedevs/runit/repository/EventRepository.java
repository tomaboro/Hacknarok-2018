package pl.hacknarok.positivedevs.runit.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import pl.hacknarok.positivedevs.runit.entity.Event;
import pl.hacknarok.positivedevs.runit.entity.Logo;
import pl.hacknarok.positivedevs.runit.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class EventRepository {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected JdbcTemplate jdbc;

    private static final RowMapper<Event> eventMapper = new RowMapper<Event>() {
        public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
            Event event = new Event(rs.getInt("ID"), rs.getString("name"),rs.getString("description"),rs.getTimestamp("start_date"),rs.getString("place"));
            return event;
        }

    };

    public List<Event> getAllEvents(){
        return jdbc.query("SELECT * FROM Event",eventMapper);
    }

    public void addEvent(Event event){
        String statement = "INSERT INTO Event VALUES('" + event.name + "','" + event.description + "','"+ event.start_date + "','" + event.place + "'," + event.latitude+ "," + event.longitude +  ")";
        jdbc.execute(statement);
    }

    private static final RowMapper<Logo> logoMapper = new RowMapper<Logo>() {
        public Logo mapRow(ResultSet rs, int rowNum) throws SQLException {
            Logo logo = new Logo(rs.getInt("ID"), rs.getInt("eventID"),rs.getString("URL"));
            return logo;
        }

    };

    public String getLogoURL(int ID){
        String statement = "SELECT URL FROM Logo WHERE EventID=" + ID;
        String URL = jdbc.queryForObject(statement,String.class);
        return URL;
    }


}