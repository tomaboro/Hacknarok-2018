package pl.hacknarok.positivedevs.runit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;




@org.springframework.web.bind.annotation.RestController
@EnableScheduling
public class RestController {

    //private final JdbcTemplateAutoConfiguration jdbcTemplate;
/*
    @Autowired
    public RestController(JdbcTemplateAutoConfiguration jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @RequestMapping(value = "/runner", method = RequestMethod.PUT)
    public void insertUser() {

        return;
        }*/
}
