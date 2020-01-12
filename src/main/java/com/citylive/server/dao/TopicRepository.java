package com.citylive.server.dao;

import com.citylive.server.domain.Topic;
import com.citylive.server.domain.TopicPK;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends CrudRepository<Topic, TopicPK> {
}
