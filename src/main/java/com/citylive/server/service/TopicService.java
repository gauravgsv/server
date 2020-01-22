package com.citylive.server.service;

import com.citylive.server.MTree.Planar.MTree2D;
import com.citylive.server.MTree.common.Data;
import com.citylive.server.dao.TopicRepository;
import com.citylive.server.dao.UserRepository;
import com.citylive.server.domain.Topic;
import com.citylive.server.pojo.Query;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    MessagingService messagingService;
    @Autowired
    MTree2D mtree;

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
