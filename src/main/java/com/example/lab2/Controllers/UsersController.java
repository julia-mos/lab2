package com.example.lab2.Controllers;

import com.example.lab2.Entities.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class UsersController {
    private Map<Integer, UserEntity> users = new HashMap<Integer, UserEntity>(){{
        put(this.size(), new UserEntity("John", 21));
        put(this.size(), new UserEntity("Adam", 25));
    }};
    private ObjectMapper objectMapper = new ObjectMapper();
    private int counter = users.size()-1;

    @RequestMapping("/users")
    @ResponseBody
    public String listUsers() {
        String response = null;
        try {
            response = objectMapper.writeValueAsString(users);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return response;
    }

    @RequestMapping("/users/{id}")
    @ResponseBody
    public String getUser(@PathVariable String id) {
        String response = null;
        UserEntity user = null;

        for (Map.Entry<Integer, UserEntity> userObject : users.entrySet()) {
            if(userObject.getKey() == Integer.parseInt(id)){
                user = userObject.getValue();
            }
        }

        if(user==null)
            return "Podany użytkownik nie istnieje";

        try {
            response = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return response;
    }

    @RequestMapping("/users/{id}/remove")
    @ResponseBody
    public String removeUser(@PathVariable String id) {
        UserEntity user = null;

        for (Map.Entry<Integer, UserEntity> userObject : users.entrySet()) {
            if(userObject.getKey() == Integer.parseInt(id)){
                user = userObject.getValue();
            }
        }

        if(user==null)
            return "Podany użytkownik nie istnieje";

        users.remove(id);

        return "Usunięto użytkownika o id "+id;
    }

    @RequestMapping("/users/add")
    @ResponseBody
    public String addUser(@RequestParam String name, @RequestParam String age) {
        counter++;
        users.put(counter, new UserEntity(name, Integer.parseInt(age)));

        return "Dodano usera o id "+counter;
    }
}
