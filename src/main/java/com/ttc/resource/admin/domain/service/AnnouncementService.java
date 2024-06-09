package com.ttc.resource.admin.domain.service;

import com.ttc.resource.admin.domain.model.entity.Announcement;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AnnouncementService {
    List<Announcement> getByFilter(Map<String, Object> parameters);
    Announcement create(Long sectionId, Long teacherId, Announcement competence);
    Announcement update(Announcement request);
    ResponseEntity<?> delete(Long announcementId);
}
