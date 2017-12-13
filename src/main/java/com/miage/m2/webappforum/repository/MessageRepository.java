package com.miage.m2.webappforum.repository;

import com.miage.m2.webappforum.entity.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, String> {
}
