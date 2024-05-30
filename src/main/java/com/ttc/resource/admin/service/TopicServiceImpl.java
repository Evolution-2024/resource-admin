package com.ttc.resource.admin.service;

import com.ttc.resource.admin.domain.model.entity.Topic;
import com.ttc.resource.admin.domain.persistence.CourseRepository;
import com.ttc.resource.admin.domain.persistence.TopicRepository;
import com.ttc.resource.admin.domain.service.TopicService;
import com.ttc.resource.shared.domain.service.communication.BaseResponse;
import com.ttc.resource.shared.exception.ResourceNotFoundException;
import com.ttc.resource.shared.exception.ResourceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TopicServiceImpl implements TopicService {
    private static final String ENTITY = "Topic";
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private Validator validator;

    @Override
    public List<Topic> getByFilter(Map<String, Object> parameters) {
        String filter = (String) parameters.get("filter");
        Long id = (Long) parameters.get("id");
        Long courseId = (Long) parameters.get("courseId");
        return topicRepository.findByFilter(filter,id,courseId);
    }

    @Override
    public Topic create(Topic request, Long courseId) {
        Set<ConstraintViolation<Topic>> violations = validator.validate(request);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);
        return courseRepository.findById(courseId).map(course -> {
            request.setId(null);
            request.setCourse(course);
            return topicRepository.save(request);
        }).orElseThrow(()->new ResourceNotFoundException("Course ",courseId));
    }

    @Override
    public Topic update(Long topicId, Topic request) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(Long itemId) {
        return null;
    }
}
