package com.ttc.resource.admin.service;

import com.ttc.resource.admin.domain.model.entity.SectionDetail;
import com.ttc.resource.admin.domain.persistence.SectionDetailRepository;
import com.ttc.resource.admin.domain.persistence.SectionRepository;
import com.ttc.resource.admin.domain.service.SectionDetailService;
import com.ttc.resource.admin.feign.UserFeignClient;
import com.ttc.resource.admin.resource.feign.User;
import com.ttc.resource.shared.domain.constants.ConstantsService;
import com.ttc.resource.shared.exception.ResourceNotFoundException;
import com.ttc.resource.shared.exception.ResourceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SectionDetailServiceImpl implements SectionDetailService {
    private static final String ENTITY = "SectionDetail";
    @Autowired
    private SectionDetailRepository sectionDetailRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private Validator validator;
    @Autowired
    private UserFeignClient feignClient;

    @Override
    public List<SectionDetail> getByFilter(Map<String, Object> parameters) {
        Long id = (Long) parameters.get("id");
        Long sectionId = (Long) parameters.get("sectionId");
        Long studentId = (Long) parameters.get("studentId");

        int page = Integer.parseInt((String) parameters.get(ConstantsService.PAGE));
        int size = Integer.parseInt((String) parameters.get(ConstantsService.SIZE));
        Pageable pageable = PageRequest.of(page, size);

        return sectionDetailRepository.findByFilter(id, sectionId, studentId, pageable).getContent();
    }

    @Override
    public SectionDetail create(Long sectionId, Long studentId, SectionDetail sectionDetail) {
        Set<ConstraintViolation<SectionDetail>> violations = validator.validate(sectionDetail);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        ResponseEntity<?> user = feignClient.getUserById(studentId);

        if(user.getStatusCode()!= HttpStatus.OK)
            throw new ResourceNotFoundException("User", studentId);

        var exist = sectionDetailRepository.existsBySectionIdAndStudentId(sectionId, studentId);
        if(exist)
            throw new ResourceNotFoundException("Estudiante ya asignado a la sección");
        if (sectionDetailRepository.existsByStudentId(studentId)) {
            throw new ResourceNotFoundException("Estudiante ya asignado a otra sección");
        }

        return sectionRepository.findById(sectionId).map(section -> {
            sectionDetail.setSection(section);
            sectionDetail.setStudentId(studentId);
            return sectionDetailRepository.save(sectionDetail);
        }).orElseThrow(()->new ResourceNotFoundException("Setion ",sectionId));
    }

//    @Override
//    public SectionDetail update(Long sectionDetailId, SectionDetail sectionDetail) {
//        Set<ConstraintViolation<SectionDetail>> violations = validator.validate(sectionDetail);
//        if (!violations.isEmpty())
//            throw new ResourceValidationException(ENTITY, violations);
//
//        return sectionDetailRepository.findById(sectionDetailId).map(data ->
//                sectionDetailRepository.save(
//                        data.withName(sectionDetail.getName())
//                                .withDescription(sectionDetail.getDescription())
//                                .withCode(sectionDetail.getCode()))
//        ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, sectionDetailId));
//    }

    @Override
    public ResponseEntity<?> delete(Long studentId) {
        return sectionDetailRepository.findByStudentId(studentId).map(item -> {
            sectionDetailRepository.delete(item);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("STUDENT", studentId));
    }
}
