package com.citylive.server.service;

import com.citylive.server.dao.TopicRepository;
import com.citylive.server.domain.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.sql.Timestamp;

@Service
@Slf4j
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    public Topic addTopic(Topic topic){
        return topicRepository.save(topic.toBuilder().closed(false).time(new Timestamp((new Date()).getTime())).build());
    }

}
