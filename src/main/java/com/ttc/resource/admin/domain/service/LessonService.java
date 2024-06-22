package com.ttc.resource.admin.domain.service;

import com.ttc.resource.admin.domain.model.entity.Lesson;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface LessonService {
    List<Lesson> getByFilter(Map<String, Object> parameters);
    Lesson create(Long sectionId, Long courseId, Lesson lesson);
    ResponseEntity<?> delete(Long lessonId);
}
