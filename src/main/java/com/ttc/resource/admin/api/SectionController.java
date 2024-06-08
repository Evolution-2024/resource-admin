package com.ttc.resource.admin.api;

import com.ttc.resource.admin.domain.service.SectionService;
import com.ttc.resource.admin.mapping.SectionMapper;
import com.ttc.resource.admin.resource.course.CourseResource;
import com.ttc.resource.admin.resource.grade.GradeResource;
import com.ttc.resource.admin.resource.section.CreateSectionResource;
import com.ttc.resource.admin.resource.section.SectionResource;
import com.ttc.resource.admin.resource.section.UpdateSectionResource;
import com.ttc.resource.shared.domain.constants.ConstantsService;
import com.ttc.resource.shared.domain.constants.DefaultParams;
import com.ttc.resource.shared.domain.service.communication.BaseResponse;
import com.ttc.resource.shared.exception.ResourceNotFoundException;
import com.ttc.resource.shared.exception.ResourceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/sections")
public class SectionController {
    @Autowired
    private SectionService sectionService;
    @Autowired
    private SectionMapper mapper;

    @GetMapping
    public  ResponseEntity<BaseResponse<List<SectionResource>>> getAll(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long gradeId,
            @RequestParam(defaultValue = DefaultParams.PAGE) String page,
            @RequestParam(defaultValue = DefaultParams.SIZE) String size
    ) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("filter", filter);
        parameters.put("id", id);
        parameters.put("gradeId", gradeId);
        parameters.put(ConstantsService.PAGE, page);
        parameters.put(ConstantsService.SIZE, size);
        BaseResponse<List<SectionResource>> response = null;
        try {
            List<SectionResource> list = mapper.modelListToResource(sectionService.getByFilter(parameters));
            response = new BaseResponse<>(list);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response = new BaseResponse<>(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<BaseResponse<SectionResource>> postSection(@RequestBody CreateSectionResource request) {
        BaseResponse<SectionResource> response = null;
        try {
            var section = sectionService.create(request.getGradeId(), mapper.toModel(request));
            SectionResource resource = mapper.toResource(section);
            response = new BaseResponse<>(resource);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceValidationException | ResourceNotFoundException e) {
            response = new BaseResponse<>(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<BaseResponse<SectionResource>> putSection(@RequestBody UpdateSectionResource request) {
        BaseResponse<SectionResource> response = null;
        try {
            var section = sectionService.update(request.getId(), mapper.toModel(request));
            SectionResource resource = mapper.toResource(section);
            response = new BaseResponse<>(resource);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceValidationException | ResourceNotFoundException e) {
            response = new BaseResponse<>(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{sectionId}")
    public ResponseEntity<?> deleteSection(@PathVariable Long sectionId) {
        return sectionService.delete(sectionId);
    }
}
