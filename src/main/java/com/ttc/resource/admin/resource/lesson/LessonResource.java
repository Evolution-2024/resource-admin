package com.ttc.resource.admin.resource.lesson;

import com.ttc.resource.admin.resource.course.CourseResource;
import com.ttc.resource.admin.resource.section.SectionResource;
import lombok.Data;

@Data
public class LessonResource {
    private Long id;
    private String teacherUsername;
    private Long teacherCode;
    private SectionResource section;
    private CourseResource course;
}
