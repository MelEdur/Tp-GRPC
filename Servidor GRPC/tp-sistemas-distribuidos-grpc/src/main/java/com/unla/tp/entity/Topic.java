package com.unla.tp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTopic;

    String topic;
}
