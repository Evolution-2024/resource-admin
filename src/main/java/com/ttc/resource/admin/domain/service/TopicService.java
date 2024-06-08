package com.ttc.resource.admin.domain.service;

import com.ttc.resource.admin.domain.model.entity.Topic;
import com.ttc.resource.shared.domain.service.communication.BaseResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface TopicService {
    List<Topic> getByFilter(Map<String, Object> parameters);
    Topic create(Topic request, Long courseId);
    Topic update(Topic request);
    ResponseEntity<?> delete(Long itemId);
}
