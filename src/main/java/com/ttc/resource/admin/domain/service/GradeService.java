package com.ttc.resource.admin.domain.service;

import com.ttc.resource.admin.domain.model.entity.Grade;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface GradeService {
    List<Grade> getByFilter(Map<String, Object> parameters);
    Grade create(Grade grade);
    Grade update(Long gradeId, Grade grade);
    ResponseEntity<?> delete(Long gradeId);
}
