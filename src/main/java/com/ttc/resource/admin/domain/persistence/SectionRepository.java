package com.ttc.resource.admin.domain.persistence;

import com.ttc.resource.admin.domain.model.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    @Query(value = "select g from Section g " +
            "where (g.name like concat('%', :name, '%') or :name is null) " +
            "and (g.code like concat('%', :code, '%') or :code is null) " +
            "and (g.id = :id or :id is null)")
    List<Section> findByFilter(
            @Param("name") String name,
            @Param("code") String code,
            @Param("id") Long id
    );
}
