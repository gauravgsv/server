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

        Topic topic1 = topicRepository.save(topic.toBuilder().closed(false).time(new Timestamp((new Date()).getTime())).build());

        List<String> nearbyUsers = mtree
                .getNearestAsList(new Data(topic.getUserName(),topic.getLongitude(),topic.getLatitude()))
                .stream()
                .map(rs->rs.data.getId())
                .collect(Collectors.toList());

        Query query = new Query();
        query.setDeviceIds(
                nearbyUsers.stream()
                        .map(userId->userRepository.getDeviceIdForUserId(userId))
                        .collect(Collectors.toList())
        );
        query.setQuestion(topic.getQuestion());
        //Query topic String ---- Topic topicId long
        //query fields not set
        try {
            messagingService.sendNotificationToMultipleDevices(query);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }

        return topic1;
    }

}
