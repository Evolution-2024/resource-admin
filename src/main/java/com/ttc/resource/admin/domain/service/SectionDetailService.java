package com.ttc.resource.admin.domain.service;

import com.ttc.resource.admin.domain.model.entity.SectionDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface SectionDetailService {
    List<SectionDetail> getByFilter(Map<String, Object> parameters);
    SectionDetail create(Long sectionId, Long studentId, SectionDetail sectionDetail);
//    SectionDetail update(Long sectionDetailId, SectionDetail sectionDetail);
    ResponseEntity<?> delete(Long studentId);
}
