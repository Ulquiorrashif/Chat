package com.example.mych.controllers;


import com.example.mych.entity.Chat;
import com.example.mych.entity.Message;
import com.example.mych.entity.MessageDTO;
import com.example.mych.services.ChatService;
import com.example.mych.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class WebsController {

    private final AtomicInteger userId = new AtomicInteger(0);
    private final ChatService chatService;
    private final MessageService messageService;

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Для подписки на топик перейдите по эндпоинту /webs";
    }

    @GetMapping("/webs")
    public ModelAndView webs() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("webs.html");
        return modelAndView;
    }

    @MessageMapping("/webs")
    @SendTo("/topic/99")
    public Message send(MessageDTO message)  {

        System.out.println(message+" ");
        Message message1 = new Message();
        message1.setText(message.text);
        message1.setAuthor(message.author);
        Chat chat = chatService.getById(message.chatId);
        message1.setChat(chat);
        messageService.save(message1);
        System.out.println(message.chatId);

        chat.getMessageList().add(message1);
        chatService.save(chat);
        System.out.println("assdsdd");
//        List<String> messageList = chat.getMessageList().stream().map(item->item.getText()).toList();
//        model.addAttribute("message",messageList);

        return message1;
    }
}