package com.unla.tp.repository;

import com.unla.tp.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITopicRepository extends JpaRepository<Topic, Integer> {

    boolean existsByTopic(String topic);
}
