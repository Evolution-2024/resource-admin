package com.ttc.resource.admin.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateCourseResource {
    @NotNull
    @NotBlank
    @Size(max = 120)
    private String name;

    @NotNull
    @NotBlank
    private String description;
}
