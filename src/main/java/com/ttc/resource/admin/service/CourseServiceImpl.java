package com.ttc.resource.admin.service;

import com.ttc.resource.admin.domain.model.entity.Course;
import com.ttc.resource.admin.domain.persistence.CourseRepository;
import com.ttc.resource.admin.domain.service.CourseService;
import com.ttc.resource.shared.domain.constants.ConstantsService;
import com.ttc.resource.shared.exception.ResourceNotFoundException;
import com.ttc.resource.shared.exception.ResourceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {
    private static final String ENTITY = "Course";
    @Autowired
    private CourseRepository courseRepository;
//    @Autowired
//    private CompetenceRepository competenceRepository;
    @Autowired
    private Validator validator;

    @Override
    public List<Course> getByFilter() {
        return null;
    }

    @Override
    public List<Course> getByFilter(Map<String, Object> parameters) {
        String filter = (String) parameters.get("filter");
        Long id = (Long) parameters.get("id");

        int page = Integer.parseInt((String) parameters.get(ConstantsService.PAGE));
        int size = Integer.parseInt((String) parameters.get(ConstantsService.SIZE));
        Pageable pageable = PageRequest.of(page, size);
        return courseRepository.findByFilter(filter,id,pageable).getContent();
    }

    @Override
    public Course getById(Long courseId) {
        return null;
    }

    @Override
    public Course create(Course course) {
        Set<ConstraintViolation<Course>> violations = validator.validate(course);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        var existingName = courseRepository.findByName(course.getName());
        if(existingName != null) {
            throw new ResourceValidationException("course is already registered");
        }

        return courseRepository.save(course);
    }

    @Override
    public Course update(Course request) {
        Set<ConstraintViolation<Course>> violations = validator.validate(request);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return courseRepository.findById(request.getId()).map(course ->
                courseRepository.save(course
                        .withName(request.getName())
                        .withDescription(request.getDescription())
                        .withCompetences(request.getCompetences())
        )).orElseThrow(() -> new ResourceNotFoundException(ENTITY, request.getId()));
    }

    @Override
    public ResponseEntity<?> delete(Long courseId) {
        return null;
    }
}
