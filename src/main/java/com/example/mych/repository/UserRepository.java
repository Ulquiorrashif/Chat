package com.example.mych.repository;

import com.example.mych.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <Users,Long> {
     Users findByEmail(String email);

    Users findByName(String name);
}
