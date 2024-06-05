package com.ttc.resource.admin.resource.course;

import com.ttc.resource.admin.resource.competence.CompetenceResource;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UpdateCourseResource {
    private Long id;
    @NotNull
    @NotBlank
    @Size(max = 300)
    private String name;

    @NotNull
    @NotBlank
    private String description;
    private List<CompetenceResource> competences;
}
