package com.ttc.resource.admin.api;

import com.ttc.resource.admin.domain.service.AnnouncementService;
import com.ttc.resource.admin.mapping.AnnouncementMapper;
import com.ttc.resource.admin.resource.announcement.AnnouncementResource;
import com.ttc.resource.admin.resource.announcement.CreateAnnouncementResource;
import com.ttc.resource.admin.resource.announcement.UpdateAnnouncementResource;
import com.ttc.resource.shared.domain.constants.ConstantsService;
import com.ttc.resource.shared.domain.constants.DefaultParams;
import com.ttc.resource.shared.domain.service.communication.BaseResponse;
import com.ttc.resource.shared.exception.ResourceNotFoundException;
import com.ttc.resource.shared.exception.ResourceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/announcements")
public class AnnouncementController {
    /*Announcements*/
    @Autowired
    AnnouncementService announcementService;
    @Autowired
    AnnouncementMapper mapper;

    @GetMapping
    public ResponseEntity<BaseResponse<List<AnnouncementResource>>> getAllAnnouncements(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long sectionCode,
            @RequestParam(required = false) Long teacherCode,
            @RequestParam(defaultValue = DefaultParams.PAGE) String page,
            @RequestParam(defaultValue = DefaultParams.SIZE) String size
    ) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("filter", filter);
        parameters.put("id", id);
        parameters.put("sectionId", sectionCode);
        parameters.put("teacherId", teacherCode);
        parameters.put(ConstantsService.PAGE, page);
        parameters.put(ConstantsService.SIZE, size);
        BaseResponse<List<AnnouncementResource>> response = null;
        try {
            List<AnnouncementResource> list = mapper.modelListToResource(announcementService.getByFilter(parameters));
            response = new BaseResponse<>(list);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response = new BaseResponse<>(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping
    public ResponseEntity<BaseResponse<AnnouncementResource>> createAnnouncement(@RequestBody CreateAnnouncementResource request) {
        BaseResponse<AnnouncementResource> response = null;
        try {
            AnnouncementResource resource = mapper.toResource(announcementService.create(request.getSectionCode(), request.getTeacherCode(), mapper.toModel(request)));
            response = new BaseResponse<>(resource);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceValidationException | ResourceNotFoundException e) {
            response = new BaseResponse<>(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping
    public ResponseEntity<BaseResponse<AnnouncementResource>> updateAnnouncement(@RequestBody UpdateAnnouncementResource request) {
        BaseResponse<AnnouncementResource> response = null;
        try {
            var announcement = announcementService.update(mapper.toModel(request));
            AnnouncementResource resource = mapper.toResource(announcement);
            response = new BaseResponse<>(resource);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceValidationException | ResourceNotFoundException e) {
            response = new BaseResponse<>(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("{announcementId}")
    public ResponseEntity<?> deleteAnnouncement(@PathVariable Long announcementId) {
        try {
            return announcementService.delete(announcementId);
        } catch (ResourceValidationException | ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

