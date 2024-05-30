package com.ttc.resource.admin.domain.persistence;

import com.ttc.resource.admin.domain.model.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByCourseId(long courseId);
    Page<Topic> findByCourseId(long courseId, Pageable pageable);
    @Query(value = "select t from Topic t " +
            "where (t.title like concat('%', :filter, '%') or :filter is null) " +
            "and (t.course.id = :courseId or :courseId is null) " +
            "and (t.id = :id or :id is null)")
    List<Topic> findByFilter(
            @Param("filter") String filter,
            @Param("id") Long id,
            @Param("courseId") Long courseId
    );
}
