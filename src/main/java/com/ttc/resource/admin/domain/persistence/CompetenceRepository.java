package com.ttc.resource.admin.domain.persistence;

import com.ttc.resource.admin.domain.model.entity.Competence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    @Query(value = "select distinct c from Competence c " +
            "left join c.courses cr " +
            "where (c.name like concat('%', :filter, '%') or :filter is null) " +
            "and (c.id = :id or :id is null) " +
            "and (cr.id = :courseId or :courseId is null)"
    )
    Page<Competence> findByFilter(
            @Param("filter") String filter,
            @Param("id") Long id,
            @Param("courseId") Long courseId,
            Pageable pageable
    );
}
