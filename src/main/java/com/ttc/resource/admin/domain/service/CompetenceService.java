package com.ttc.resource.admin.domain.service;

import com.ttc.resource.admin.domain.model.entity.Competence;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CompetenceService {
    List<Competence> getByFilter(Map<String, Object> parameters);
    Competence create(Competence competence);
    Competence update(Competence request);
    ResponseEntity<?> delete(Long competenceId);
}
