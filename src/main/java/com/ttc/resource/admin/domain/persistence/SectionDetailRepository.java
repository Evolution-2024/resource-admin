package com.ttc.resource.admin.domain.persistence;

import com.ttc.resource.admin.domain.model.entity.Grade;
import com.ttc.resource.admin.domain.model.entity.SectionDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionDetailRepository extends JpaRepository<SectionDetail, Long> {

    @Query(value = "select g from SectionDetail g " +
            "where (g.id = :id or :id is null) " +
            "and (g.section.id = :sectionId or :sectionId is null) " +
            "and (g.studentId = :studentId or :studentId is null) ")
    Page<SectionDetail> findByFilter(
            @Param("id") Long id,
            @Param("sectionId") Long sectionId,
            @Param("studentId") Long studentId,
            Pageable pageable
    );
}
