package com.ttc.resource.admin.service;

import com.ttc.resource.admin.domain.model.entity.Announcement;
import com.ttc.resource.admin.domain.model.entity.Competence;
import com.ttc.resource.admin.domain.model.entity.SectionDetail;
import com.ttc.resource.admin.domain.persistence.AnnouncementRepository;
import com.ttc.resource.admin.domain.persistence.SectionRepository;
import com.ttc.resource.admin.domain.service.AnnouncementService;
import com.ttc.resource.admin.feign.UserFeignClient;
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
public class AnnouncementServiceImpl implements AnnouncementService {
    private static final String ENTITY = "Announcement";

    private final AnnouncementRepository announcementRepository;
    private final SectionRepository sectionRepository;
    private final Validator validator;
    @Autowired
    private UserFeignClient feignClient;

    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository, SectionRepository sectionRepository, Validator validator) {
        this.announcementRepository = announcementRepository;
        this.sectionRepository = sectionRepository;
        this.validator = validator;
    }

    @Override
    public List<Announcement> getByFilter(Map<String, Object> parameters) {
        String filter = (String) parameters.get("filter");
        Long id = (Long) parameters.get("id");
        Long sectionId = (Long) parameters.get("sectionId");
        Long studentId = (Long) parameters.get("studentId");

        int page = Integer.parseInt((String) parameters.get(ConstantsService.PAGE));
        int size = Integer.parseInt((String) parameters.get(ConstantsService.SIZE));
        Pageable pageable = PageRequest.of(page, size);
        return announcementRepository.findByFilter(filter,id,sectionId, studentId, pageable).getContent();
    }

    @Override
    public Announcement create(Long sectionId, Long studentId, Announcement announcement) {
        Set<ConstraintViolation<Announcement>> violations = validator.validate(announcement);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        ResponseEntity<?> user = feignClient.getUserById(studentId);

        if(user.getStatusCode()!= HttpStatus.OK)
            throw new ResourceNotFoundException("User", studentId);

        return sectionRepository.findById(sectionId).map(section -> {
            announcement.setSectionId(section.getId());
            announcement.setStudentId(studentId);
            return announcementRepository.save(announcement);
        }).orElseThrow(()->new ResourceNotFoundException("Section ", announcement.getSectionId()));
    }

    @Override
    public Announcement update(Announcement request) {
        Set<ConstraintViolation<Announcement>> violations = validator.validate(request);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return announcementRepository.findById(request.getId()).map(competence ->
                announcementRepository.save(competence
                        .withTitle(request.getTitle())
                        .withDescription(request.getDescription())
                )).orElseThrow(() -> new ResourceNotFoundException(ENTITY, request.getId()));
    }

    @Override
    public ResponseEntity<?> delete(Long announcementId) {
        return announcementRepository.findById(announcementId).map(data -> {
            announcementRepository.delete(data);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, announcementId));
    }
}
