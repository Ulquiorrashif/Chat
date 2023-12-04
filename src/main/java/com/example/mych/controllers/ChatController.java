package com.example.mych.controllers;

import com.example.mych.entity.Chat;
import com.example.mych.entity.Message;
import com.example.mych.entity.Users;
import com.example.mych.services.ChatService;
import com.example.mych.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@Data
@RequestMapping("/api/chats")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @GetMapping("/create")
    public Long createUser(
            @AuthenticationPrincipal UserDetails userDetails,
            String name
    ) {
        System.out.println(
                userDetails.getUsername()
                        +" "+
                        name
        );
        chatService.create(userDetails,name);
        return chatService.getDataFromDatabase(userDetails.getUsername(),name).get(0).getId();
    }
    @GetMapping("/show")
    public List<Chat> show(String me, String name2  ) {

        return chatService.getDataFromDatabase(me, name2);
    }
    @GetMapping("/get/message")
    public List<Message>  getMessageByChat(Long id  ) {
            System.out.println(id +" "+"get");
        return chatService.geMessageByChat(id);
    }
}
