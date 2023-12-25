package com.example.mych.controllers;


import com.example.mych.entity.Chat;
import com.example.mych.entity.Users;
import com.example.mych.repository.UserRepository;
import com.example.mych.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService; // Предположим, что у вас есть репозиторий для сущности User

    @GetMapping("/create/{id}")
    public List<Chat> get( @PathVariable Long id) {
        return  userService.get(id);
    }
    @GetMapping("/get")
    public Users gets( String name) {
        System.out.println(name);
        return  userService.getByName(name);
    }

    @PostMapping("/create")
    public String createUser( Users users) {
        System.out.println(users.toString());
        List<Users> list  = userService.getAllUsers();
        if (list.size()!=0){
            userService.registration(users);
            for (Users item:list) {
                item.getFriendsList().add(users);
                userService.save(item);
                users.getFriendsList().add(item);
                userService.save(users);
            }
            return "Users created and added as friends.";
        }
        userService.registration(users);
        return "Users created and added as friends.";
    }
}