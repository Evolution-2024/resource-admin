package com.ttc.resource.admin.resource.lesson;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateLessonResource {
    @NotNull
    @NotBlank
    private String teacherUsername;
    @NotNull
    private Long teacherCode;
    private Long sectionCode;
    private Long courseCode;
}
