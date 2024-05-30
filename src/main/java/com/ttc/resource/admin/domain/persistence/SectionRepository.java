package com.ttc.resource.admin.domain.persistence;

import com.ttc.resource.admin.domain.model.entity.Grade;
import com.ttc.resource.admin.domain.model.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    @Query(value = "select g from Grade g " +
            "where (g.name like concat('%', :filter, '%') or :filter is null) " +
            "and (g.id = :id or :id is null)")
    List<Section> findByFilter(
            @Param("filter") String filter,
            @Param("id") Long id
    );
}
