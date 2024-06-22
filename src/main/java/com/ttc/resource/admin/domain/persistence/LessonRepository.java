package com.ttc.resource.admin.domain.persistence;

import com.ttc.resource.admin.domain.model.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query(value = "select l from Lesson l " +
            "where (l.id = :id or :id is null) " +
            "and (l.section.id = :sectionId or :sectionId is null) " +
            "and (l.teacherCode = :teacherCode or :teacherCode is null) " +
            "and (l.course.id = :courseId or :courseId is null) ")
    Page<Lesson> findByFilter(
            @Param("id") Long id,
            @Param("sectionId") Long sectionId,
            @Param("courseId") Long courseId,
            @Param("teacherCode") Long teacherCode,
            Pageable pageable
    );
    boolean existsBySectionIdAndCourseIdAndTeacherCode(Long sectionId, Long courseId,Long teacherCode);
}
