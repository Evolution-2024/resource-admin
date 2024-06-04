package com.ttc.resource.admin.service;

import com.ttc.resource.admin.domain.model.entity.Grade;
import com.ttc.resource.admin.domain.model.entity.Section;
import com.ttc.resource.admin.domain.persistence.GradeRepository;
import com.ttc.resource.admin.domain.persistence.SectionRepository;
import com.ttc.resource.admin.domain.service.SectionService;
import com.ttc.resource.shared.domain.constants.ConstantsService;
import com.ttc.resource.shared.exception.ResourceNotFoundException;
import com.ttc.resource.shared.exception.ResourceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SectionServiceImpl implements SectionService {
    private static final String ENTITY = "Section";
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private Validator validator;

    @Override
    public List<Section> getByFilter(Map<String, Object> parameters) {
        Long id = (Long) parameters.get("id");
        Long gradeId = (Long) parameters.get("gradeId");
        String filter = (String) parameters.get("filter");

        int page = Integer.parseInt((String) parameters.get(ConstantsService.PAGE));
        int size = Integer.parseInt((String) parameters.get(ConstantsService.SIZE));
        Pageable pageable = PageRequest.of(page, size);

        return sectionRepository.findByFilter(filter, id, gradeId, pageable).getContent();
    }

    @Override
    public Section create(Long gradeId, Section section) {
        Set<ConstraintViolation<Section>> violations = validator.validate(section);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);
        return gradeRepository.findById(gradeId).map(grade -> {
            section.setGrade(grade);
            return sectionRepository.save(section);
        }).orElseThrow(()->new ResourceNotFoundException("Grade ",gradeId));
    }

    @Override
    public Section update(Long sectionId, Section section) {
        Set<ConstraintViolation<Section>> violations = validator.validate(section);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return sectionRepository.findById(sectionId).map(data ->
                sectionRepository.save(
                        data.withName(section.getName())
                                .withDescription(section.getDescription())
                                .withCode(section.getCode()))
        ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, sectionId));
    }

    @Override
    public ResponseEntity<?> delete(Long sectionId) {
        return sectionRepository.findById(sectionId).map(item -> {
            sectionRepository.delete(item);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, sectionId));
    }
}
