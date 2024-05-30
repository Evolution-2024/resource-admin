package com.ttc.resource.admin.domain.service;

import com.ttc.resource.admin.domain.model.entity.Section;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface SectionService {
    List<Section> getByFilter(Map<String, Object> parameters);
    Section create(Long gradeId, Section section);
    Section update(Long sectionId, Section section);
    ResponseEntity<?> delete(Long sectionId);
}
