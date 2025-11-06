package com.chatop.rental.repositories;

import com.chatop.rental.entites.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long>{
}
