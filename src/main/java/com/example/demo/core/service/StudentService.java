package com.example.demo.core.service;

import java.io.Serializable;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Student;

@Service
public class StudentService {

    private final KafkaTemplate<String, Serializable> kafkaTemplate;

    public StudentService(KafkaTemplate<String, Serializable> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(Student student) {
        System.out.println("Producing message: " + student);
        kafkaTemplate.send("topic1", student);
    }

}
