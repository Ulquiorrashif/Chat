package com.example.mych.services;

import com.example.mych.entity.Chat;
import com.example.mych.entity.Message;
import com.example.mych.entity.Users;
import com.example.mych.repository.ChatRepository;
import com.example.mych.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRep;
    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;
    public void create(UserDetails userDetails, String name ){
        if (getDataFromDatabase(userDetails.getUsername(),name).size()==0){
            Users user1 = userRepository.findByName(userDetails.getUsername());

            System.out.println(userDetails.getUsername()+" "+name);
            Chat chat = new Chat();
            chatRep.save(chat);
            Users user = userRepository.findByName(name);

            user.getChatList().add(chat);
            user1.getChatList().add(chat);
            userRepository.save(user);
            userRepository.save(user1);
        }
    };
    public Chat getById(Long id){
        Chat chatOptional = chatRep.findById(id).get();
        return chatOptional;

    }
    public List<Chat> getDataFromDatabase(String me, String name2) {
        String sql = "SELECT c.* FROM chat c WHERE EXISTS (SELECT 1 FROM users_chat_list WHERE chat_list_id = c.id AND users_id = (SELECT id FROM users WHERE name = ?)) AND EXISTS (SELECT 1 FROM users_chat_list as cu2 WHERE cu2.chat_list_id = c.id AND cu2.users_id = (SELECT id FROM users WHERE name = ?));";

        return jdbcTemplate.query(sql,new Object[]{me, name2}, (rs) -> {
            List<Chat> chatList = new ArrayList<>();
            while (rs.next()) {
                Chat entity = new Chat();
                entity.setId(rs.getLong("id"));
                // Здесь устанавливайте другие поля сущности
                chatList.add(entity);
            }
            return chatList;
        });



    }
    public List<Message> geMessageByChat(Long id){
        return chatRep.findById(id).get().getMessageList();
    }

    public void save(Chat chat) {
        chatRep.save(chat);
    }
}
