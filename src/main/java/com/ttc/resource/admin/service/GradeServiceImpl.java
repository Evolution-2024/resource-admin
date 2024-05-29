package com.ttc.resource.admin.service;

import com.ttc.resource.admin.domain.model.entity.Grade;
import com.ttc.resource.admin.domain.persistence.GradeRepository;
import com.ttc.resource.admin.domain.service.GradeService;
import com.ttc.resource.shared.exception.ResourceNotFoundException;
import com.ttc.resource.shared.exception.ResourceValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class GradeServiceImpl implements GradeService {
    private static final String ENTITY = "Grade";
    private final GradeRepository gradeRepository;
    private final Validator validator;

    public GradeServiceImpl(GradeRepository gradeRepository, Validator validator) {
        this.gradeRepository = gradeRepository;
        this.validator = validator;
    }

    @Override
    public List<Grade> getByFilter(Map<String, Object> parameters) {
        Long id = (Long) parameters.get("id");
        String filter = (String) parameters.get("filter");
        return gradeRepository.findByFilter(filter, id);
    }

    @Override
    public Grade create(Grade grade) {
        Set<ConstraintViolation<Grade>> violations = validator.validate(grade);

        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return gradeRepository.save(grade);
    }

    @Override
    public Grade update(Long gradeId, Grade grade) {
        Set<ConstraintViolation<Grade>> violations = validator.validate(grade);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return gradeRepository.findById(gradeId).map(data ->
                gradeRepository.save(
                        data.withName(grade.getName())
                                .withDescription(grade.getDescription()))
        ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, gradeId));
    }

    @Override
    public ResponseEntity<?> delete(Long gradeId) {
        return gradeRepository.findById(gradeId).map(announcement -> {
            gradeRepository.delete(announcement);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, gradeId));
    }
}
