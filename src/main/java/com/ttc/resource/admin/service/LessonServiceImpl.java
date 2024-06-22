package com.ttc.resource.admin.service;

import com.ttc.resource.admin.domain.model.entity.Lesson;
import com.ttc.resource.admin.domain.persistence.CourseRepository;
import com.ttc.resource.admin.domain.persistence.LessonRepository;
import com.ttc.resource.admin.domain.persistence.SectionRepository;
import com.ttc.resource.admin.domain.service.LessonService;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class LessonServiceImpl implements LessonService {
    private static final String ENTITY = "Lesson";
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private Validator validator;
    @Override
    public List<Lesson> getByFilter(Map<String, Object> parameters) {
        Long id = (Long) parameters.get("id");
        Long courseId = (Long) parameters.get("courseId");
        Long sectionId = (Long) parameters.get("sectionId");
        Long teacherCode = (Long) parameters.get("teacherCode");

        int page = Integer.parseInt((String) parameters.get(ConstantsService.PAGE));
        int size = Integer.parseInt((String) parameters.get(ConstantsService.SIZE));

        Pageable pageable = PageRequest.of(page, size);
        return lessonRepository.findByFilter(id,sectionId,courseId,teacherCode,pageable).getContent();
    }

    @Override
    public Lesson create(Long sectionId, Long courseId, Lesson lesson) {
        Set<ConstraintViolation<Lesson>> violations = validator.validate(lesson);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        if(lessonRepository.existsBySectionIdAndCourseIdAndTeacherCode(sectionId,courseId,lesson.getTeacherCode()))
            throw new ResourceValidationException("Este profesor ya enseña el curso en la sección");

        System.out.println("LESSON => ");
        System.out.println(lesson);
        return sectionRepository.findById(sectionId).map(section -> {
            lesson.setSection(section);
            return courseRepository.findById(courseId).map(course -> {
                lesson.setCourse(course);
                return lessonRepository.save(lesson);
            }).orElseThrow(()->new ResourceNotFoundException("Course ",courseId));
        }).orElseThrow(()->new ResourceNotFoundException("Setion ",sectionId));
    }

    @Override
    public ResponseEntity<?> delete(Long lessonId) {
        return lessonRepository.findById(lessonId).map(item -> {
            lessonRepository.delete(item);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, lessonId));
    }
}
