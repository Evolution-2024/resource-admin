package com.ttc.resource.admin.domain.persistence;

import com.ttc.resource.admin.domain.model.entity.Course;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("select d from Course d where d.name = ?1")
    Course findByName(String name);

    @Query(value = "select d from Course d" +
            "where ()")
    List<Course> findByFilter();

}
