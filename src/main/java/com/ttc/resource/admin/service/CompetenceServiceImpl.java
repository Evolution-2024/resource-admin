package com.ttc.resource.admin.service;

import com.ttc.resource.admin.domain.model.entity.Competence;
import com.ttc.resource.admin.domain.persistence.CompetenceRepository;
import com.ttc.resource.admin.domain.service.CompetenceService;
import com.ttc.resource.shared.domain.constants.ConstantsService;
import com.ttc.resource.shared.exception.ResourceValidationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CompetenceServiceImpl implements CompetenceService {
    private static final String ENTITY = "Competence";

    private final CompetenceRepository competenceRepository;
    private final Validator validator;

    public CompetenceServiceImpl(CompetenceRepository competenceRepository, Validator validator) {
        this.competenceRepository = competenceRepository;
        this.validator = validator;
    }

    @Override
    public List<Competence> getByFilter(Map<String, Object> parameters) {
        String filter = (String) parameters.get("filter");
        Long id = (Long) parameters.get("id");
        Long courseId = (Long) parameters.get("courseId");

        int page = Integer.parseInt((String) parameters.get(ConstantsService.PAGE));
        int size = Integer.parseInt((String) parameters.get(ConstantsService.SIZE));
        Pageable pageable = PageRequest.of(page, size);
        return competenceRepository.findByFilter(filter,id,courseId,pageable).getContent();
    }

    @Override
    public Competence create(Competence competence) {
        Set<ConstraintViolation<Competence>> violations = validator.validate(competence);
        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return competenceRepository.save(competence);
    }

    @Override
    public Competence update(Long competenceId, Competence request) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(Long competenceId) {
        return null;
    }
}
