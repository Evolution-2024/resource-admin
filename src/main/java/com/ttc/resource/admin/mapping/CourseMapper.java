package com.ttc.resource.admin.mapping;

import com.ttc.resource.admin.domain.model.entity.Course;
import com.ttc.resource.admin.resource.course.CourseResource;
import com.ttc.resource.admin.resource.course.CreateCourseResource;
import com.ttc.resource.admin.resource.course.UpdateCourseResource;
import com.ttc.resource.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class CourseMapper implements Serializable {

    @Autowired
    EnhancedModelMapper mapper;

    public CourseResource toResource(Course model) {
        return mapper.map(model, CourseResource.class);
    }

    public Page<CourseResource> modelListToPage(List<Course> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, CourseResource.class), pageable, modelList.size());
    }

    public List<CourseResource> modelListToResource(List<Course> modelList) {
        return mapper.mapList(modelList, CourseResource.class);
    }

    public Course toModel(CreateCourseResource resource) {
        return mapper.map(resource, Course.class);
    }

    public Course toModel(UpdateCourseResource resource) {
        return mapper.map(resource, Course.class);
    }
}