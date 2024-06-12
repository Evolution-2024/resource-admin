package com.ttc.resource.admin.service;

import com.ttc.resource.admin.domain.model.entity.Topic;
import com.ttc.resource.admin.domain.persistence.CourseRepository;
import com.ttc.resource.admin.domain.persistence.TopicRepository;
import com.ttc.resource.admin.domain.service.TopicService;
import com.ttc.resource.shared.domain.constants.ConstantsService;
import com.ttc.resource.shared.exception.ResourceNotFoundException;
import com.ttc.resource.shared.exception.ResourceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Base64;
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

        int page = Integer.parseInt((String) parameters.get(ConstantsService.PAGE));
        int size = Integer.parseInt((String) parameters.get(ConstantsService.SIZE));
        Pageable pageable = PageRequest.of(page, size);
        List<Topic> list = topicRepository.findByFilter(filter,id,courseId,pageable).getContent();
        list.forEach(p -> {
            if (p.getFile_data() != null)
                p.setFile(Base64.getEncoder().encodeToString(p.getFile_data()));
        });
        return list;
    }

    @Override
    public Topic create(Topic request, Long courseId, String file) {
        Set<ConstraintViolation<Topic>> violations = validator.validate(request);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);
        if (file != null) {
            byte[] fileBytes = Base64.getDecoder().decode(file);
            request.setFile_data(fileBytes);
        }
        return courseRepository.findById(courseId).map(course -> {
            request.setId(null);
            request.setCourse(course);
            return topicRepository.save(request);
        }).orElseThrow(()->new ResourceNotFoundException("Course ",courseId));
    }

    @Override
    public Topic update(Topic request, String file) {
        Set<ConstraintViolation<Topic>> violations = validator.validate(request);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        if (file != null) {
            byte[] fileBytes = Base64.getDecoder().decode(file);
            request.setFile_data(fileBytes);
        }

        return topicRepository.findById(request.getId()).map(topic ->
                topicRepository.save(topic
                        .withTitle(request.getTitle())
                        .withDescription(request.getDescription())
                        .withFile(request.getFile())
                        .withFile_data(request.getFile_data())
                )).orElseThrow(() -> new ResourceNotFoundException(ENTITY, request.getId()));
    }

    @Override
    public ResponseEntity<?> delete(Long itemId) {
        return topicRepository.findById(itemId).map(topic -> {
            topicRepository.delete(topic);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, itemId));
    }
}
