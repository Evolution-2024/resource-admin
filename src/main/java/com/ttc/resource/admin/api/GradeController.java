package com.ttc.resource.admin.api;

import com.ttc.resource.admin.domain.model.entity.Grade;
import com.ttc.resource.admin.domain.service.GradeService;
import com.ttc.resource.admin.mapping.GradeMapper;
import com.ttc.resource.admin.resource.course.CourseResource;
import com.ttc.resource.admin.resource.grade.CreateGradeResource;
import com.ttc.resource.admin.resource.grade.GradeResource;
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
@RequestMapping("api/v1/grades")
public class GradeController {
    @Autowired
    private GradeService gradeService;
    @Autowired
    private GradeMapper mapper;

    @GetMapping()
    public ResponseEntity<BaseResponse<List<GradeResource>>> getAll(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) Long id,
            @RequestParam(defaultValue = DefaultParams.PAGE) String page,
            @RequestParam(defaultValue = DefaultParams.SIZE) String size
    ) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("filter", filter);
        parameters.put("id", id);
        parameters.put(ConstantsService.PAGE, page);
        parameters.put(ConstantsService.SIZE, size);
        BaseResponse<List<GradeResource>> response = null;
        try {
            List<GradeResource> list = mapper.modelListToResource(gradeService.getByFilter(parameters));
            response = new BaseResponse<>(list);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response = new BaseResponse<>(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<BaseResponse<GradeResource>> postGrade(@RequestBody CreateGradeResource request) {
        BaseResponse<GradeResource> response = null;
        try {
            var grade = gradeService.create(mapper.toModel(request));
            GradeResource resource = mapper.toResource(grade);
            response = new BaseResponse<>(resource);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceValidationException | ResourceNotFoundException e) {
            response = new BaseResponse<>(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
//
//    @PutMapping("{gradeId}")
//    public Grade putGrade(@PathVariable Long gradeId, @RequestBody UpdateGradeResource resource) {
//        return gradeService.update(gradeId, mapper.toModel(resource));
//    }
//
//    @DeleteMapping("{gradeId}")
//    public ResponseEntity<?> deleteGrade(@PathVariable Long gradeId) {
//        return gradeService.delete(gradeId);
//    }
}
