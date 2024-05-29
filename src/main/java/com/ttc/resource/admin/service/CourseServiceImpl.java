package com.ttc.resource.admin.service;

import com.ttc.resource.admin.domain.model.entity.Course;
import com.ttc.resource.admin.domain.persistence.CourseRepository;
import com.ttc.resource.admin.domain.service.CourseService;
import com.ttc.resource.shared.exception.ResourceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public List<Course> getAll() {
        return null;
    }

    @Override
    public List<Course> getAll(Map<String, Object> parameters) {
        String filter = (String) parameters.get("filter");
        Long id = (Long) parameters.get("id");
        return courseRepository.findByFilter(filter,id);
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
    public Course update(Long courseId, Course request) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(Long courseId) {
        return null;
    }
}
