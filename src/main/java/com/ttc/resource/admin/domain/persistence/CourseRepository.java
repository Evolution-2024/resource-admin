package com.ttc.resource.admin.domain.persistence;

import com.ttc.resource.admin.domain.model.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("select d from Course d where d.name = ?1")
    Course findByName(String name);

    @Query(value = "select d from Course d " +
            "where (d.name like concat('%', :filter, '%') or :filter is null) " +
            "and (d.id = :id or :id is null)")
    Page<Course> findByFilter(
            @Param("filter") String filter,
            @Param("id") Long id,
            Pageable pageable
    );

}
