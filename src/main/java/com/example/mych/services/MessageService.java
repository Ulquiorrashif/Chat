package com.example.mych.services;

import com.example.mych.entity.Message;
import com.example.mych.repository.MessageRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
public class MessageService {
    private final MessageRepository messageRepository;
    public void save (Message message){
        messageRepository.save(message);
    }
}
