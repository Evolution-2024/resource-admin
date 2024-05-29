package com.ttc.resource.admin.api;

import com.ttc.resource.admin.domain.service.CourseService;
import com.ttc.resource.admin.mapping.CourseMapper;
import com.ttc.resource.admin.resource.course.CourseResource;
import com.ttc.resource.admin.resource.course.CreateCourseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public Page<CourseResource> getAllCourses(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) Long id,
            Pageable pageable
    ) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("filter", filter);
        parameters.put("id", id);
        return mapper.modelListToPage(courseService.getAll(parameters), pageable);
    }
    @PostMapping
    public CourseResource createCourse(@RequestBody CreateCourseResource request) {

        var course = courseService.create(mapper.toModel(request));
        return mapper.toResource(course);
    }
}
