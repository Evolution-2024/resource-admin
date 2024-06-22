package com.ttc.resource.admin.api;

import com.ttc.resource.admin.domain.service.LessonService;
import com.ttc.resource.admin.mapping.LessonMapper;
import com.ttc.resource.admin.resource.lesson.CreateLessonResource;
import com.ttc.resource.admin.resource.lesson.LessonResource;
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
@RequestMapping("api/v1/lesson")
public class LessonController {
    @Autowired
    private LessonService lessonService;
    @Autowired
    private LessonMapper mapper;

    @GetMapping
    public ResponseEntity<BaseResponse<List<LessonResource>>> getAll(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long teacherCode,
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false) Long courseId,
            @RequestParam(defaultValue = DefaultParams.PAGE) String page,
            @RequestParam(defaultValue = DefaultParams.SIZE) String size
    ) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);
        parameters.put("teacherCode", teacherCode);
        parameters.put("sectionId", sectionId);
        parameters.put("courseId", courseId);
        parameters.put(ConstantsService.PAGE, page);
        parameters.put(ConstantsService.SIZE, size);
        BaseResponse<List<LessonResource>> response = null;
        try {
            List<LessonResource> list = mapper.modelListToResource(lessonService.getByFilter(parameters));
            response = new BaseResponse<>(list);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response = new BaseResponse<>(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<BaseResponse<LessonResource>> postSectionDetail(@RequestBody CreateLessonResource request) {
        BaseResponse<LessonResource> response = null;
        try {
            var sectionDetail = lessonService.create(request.getSectionCode(), request.getCourseCode(), mapper.toModel(request));
            LessonResource resource = mapper.toResource(sectionDetail);
            response = new BaseResponse<>(resource);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceValidationException | ResourceNotFoundException e) {
            response = new BaseResponse<>(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("{lessonId}")
    public ResponseEntity<?> deleteSectionDetail(@PathVariable Long lessonId) {
        return lessonService.delete(lessonId);
    }
}
