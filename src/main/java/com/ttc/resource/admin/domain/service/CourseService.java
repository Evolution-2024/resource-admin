package com.ttc.resource.admin.domain.service;

import com.ttc.resource.admin.domain.model.entity.Course;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CourseService {
    List<Course> getByFilter();
     List<Course> getByFilter(Map<String, Object> parameters);
    Course getById(Long courseId);
    Course create(Course course);
    Course update(Long courseId, Course request);
    ResponseEntity<?> delete(Long courseId);
//    List<Competence> getAllCompetences_Course(Long courseId);
//    void linkCompetencesToCourse(Long courseId, List<Long> competenceIds);
}
