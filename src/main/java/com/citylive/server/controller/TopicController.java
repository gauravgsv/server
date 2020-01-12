package com.citylive.server.controller;

import com.citylive.server.domain.Topic;
import com.citylive.server.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @PostMapping
    public Topic addTopic(@RequestBody @Validated Topic topic){
        return topicService.addTopic(topic);
    }
}
