package com.ttc.resource.admin.domain.persistence;

import com.ttc.resource.admin.domain.model.entity.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    @Query(value = "select g from Section g " +
            "where (g.name like concat('%', :filter, '%') or g.code like concat('%', :filter, '%') or :filter is null) " +
            "and (g.grade.id = :gradeId or :gradeId is null) " +
            "and (g.id = :id or :id is null)")
    Page<Section> findByFilter(
            @Param("filter") String filter,
            @Param("id") Long id,
            @Param("gradeId") Long gradeId,
            Pageable pageable
    );
}
