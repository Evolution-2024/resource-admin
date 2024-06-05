package com.ttc.resource.admin.api;

import com.ttc.resource.admin.domain.service.CourseService;
import com.ttc.resource.admin.mapping.CourseMapper;
import com.ttc.resource.admin.resource.course.CourseResource;
import com.ttc.resource.admin.resource.course.CreateCourseResource;
import com.ttc.resource.admin.resource.course.UpdateCourseResource;
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
@RequestMapping("api/v1/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseMapper mapper;
//    @Autowired
//    CompetenceMapper competenceMapper;

    @GetMapping
    public ResponseEntity<BaseResponse<List<CourseResource>>> getAllCourses(
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
        BaseResponse<List<CourseResource>> response = null;
        try {
            List<CourseResource> list = mapper.modelListToResource(courseService.getByFilter(parameters));
            response = new BaseResponse<>(list);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response = new BaseResponse<>(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping
    public ResponseEntity<BaseResponse<CourseResource>> createCourse(@RequestBody CreateCourseResource request) {

        BaseResponse<CourseResource> response = null;
        try {
            var course = courseService.create(mapper.toModel(request));
            CourseResource resource = mapper.toResource(course);
            response = new BaseResponse<>(resource);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceValidationException | ResourceNotFoundException e) {
            response = new BaseResponse<>(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping
    public ResponseEntity<BaseResponse<CourseResource>> updateCourse(@RequestBody UpdateCourseResource request) {
        BaseResponse<CourseResource> response = null;
        try {
            var course = courseService.update(mapper.toModel(request));
            CourseResource resource = mapper.toResource(course);
            response = new BaseResponse<>(resource);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceValidationException | ResourceNotFoundException e) {
            response = new BaseResponse<>(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
