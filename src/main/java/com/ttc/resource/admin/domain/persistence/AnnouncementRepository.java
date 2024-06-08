package com.ttc.resource.admin.domain.persistence;

import com.ttc.resource.admin.domain.model.entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query(value = "select g from Announcement g " +
            "where (g.title like concat('%', :filter, '%') or :filter is null) " +
            "and (g.id = :id or :id is null) " +
            "and (g.sectionId = :sectionId or :sectionId is null) " +
            "and (g.studentId = :studentId or :studentId is null) ")
    Page<Announcement> findByFilter(
            @Param("filter") String filter,
            @Param("id") Long id,
            @Param("sectionId") Long sectionId,
            @Param("studentId") Long studentId,
            Pageable pageable
    );
}