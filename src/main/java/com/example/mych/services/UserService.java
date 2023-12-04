package com.example.mych.services;


import com.example.mych.entity.Chat;
import com.example.mych.entity.Users;
import com.example.mych.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository usersRep;
    private final PasswordEncoder passwordEncoder;
    public void registration(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user.getEmail()+" "+ user.getPassword());
        usersRep.save(user);
    }
    public void save(Users user){

        usersRep.save(user);
    }
    public List<Users> getAllUsers(){
        return usersRep.findAll();
    }

    public List<Chat> get(Long id) {
        return usersRep.findById(id).get().getChatList();
    }

    public Users getByName(String name) {
        return  usersRep.findByName(name);
    }
}