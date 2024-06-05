package com.ttc.resource.admin.api;

import com.ttc.resource.admin.domain.service.SectionDetailService;
import com.ttc.resource.admin.mapping.SectionDetailMapper;
import com.ttc.resource.admin.resource.sectiondetail.CreateSectionDetailResource;
import com.ttc.resource.admin.resource.sectiondetail.SectionDetailResource;
import com.ttc.resource.admin.resource.sectiondetail.UpdateSectionDetailResource;
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
@RequestMapping("api/v1/sectionDetails")
public class SectionDetailController {
    @Autowired
    private SectionDetailService sectionDetailService;
    @Autowired
    private SectionDetailMapper mapper;

    @GetMapping
    public  ResponseEntity<BaseResponse<List<SectionDetailResource>>> getAll(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long sectionId,
            @RequestParam(defaultValue = DefaultParams.PAGE) String page,
            @RequestParam(defaultValue = DefaultParams.SIZE) String size
    ) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);
        parameters.put("studentId", studentId);
        parameters.put("gradeId", sectionId);
        parameters.put(ConstantsService.PAGE, page);
        parameters.put(ConstantsService.SIZE, size);
        BaseResponse<List<SectionDetailResource>> response = null;
        try {
            List<SectionDetailResource> list = mapper.modelListToResource(sectionDetailService.getByFilter(parameters));
            response = new BaseResponse<>(list);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response = new BaseResponse<>(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<BaseResponse<SectionDetailResource>> postSectionDetail(@RequestBody CreateSectionDetailResource request) {
        BaseResponse<SectionDetailResource> response = null;
        try {
            var sectionDetail = sectionDetailService.create(request.getSectionCode(), request.getStudentCode(), mapper.toModel(request));
            SectionDetailResource resource = mapper.toResource(sectionDetail);
            response = new BaseResponse<>(resource);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceValidationException | ResourceNotFoundException e) {
            response = new BaseResponse<>(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /*@PutMapping
    public ResponseEntity<BaseResponse<SectionDetailResource>> putSectionDetail(@RequestBody UpdateSectionDetailResource request) {
        BaseResponse<SectionDetailResource> response = null;
        try {
            var sectionDetail = sectionDetailService.update(request.getSectionDetailId(), mapper.toModel(request));
            SectionDetailResource resource = mapper.toResource(sectionDetail);
            response = new BaseResponse<>(resource);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceValidationException | ResourceNotFoundException e) {
            response = new BaseResponse<>(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{sectionDetailId}")
    public ResponseEntity<?> deleteSectionDetail(@PathVariable Long sectionDetailId) {
        return sectionDetailService.delete(sectionDetailId);
    }*/
}
