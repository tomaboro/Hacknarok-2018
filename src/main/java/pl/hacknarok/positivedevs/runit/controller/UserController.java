package pl.hacknarok.positivedevs.runit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import pl.hacknarok.positivedevs.runit.entity.User;
import pl.hacknarok.positivedevs.runit.repository.UserRepository;

@RestController
@RequestMapping("user")
public class UserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository users;

    @RequestMapping(value = "create", method = RequestMethod.PUT)
    public String createUser(@RequestBody User user){
        if(users.checkIfUserExists(user.name))
            return "User with that username already exists!";
        else {
            users.addUser(user.name,user.password);
            return "User created successfully";
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String loginUser(@RequestBody User user){
        String uuid = users.login(user.name,user.password);
        if(uuid == null)
            return null;
        else {
            return uuid;
        }
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public void logoutUser(@RequestBody User user){
        users.logout(user.token);
    }

}