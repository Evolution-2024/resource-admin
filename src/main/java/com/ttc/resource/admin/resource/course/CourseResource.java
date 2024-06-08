package com.ttc.resource.admin.resource.course;

import com.ttc.resource.admin.resource.competence.CompetenceResource;
import lombok.Data;

import java.util.List;

@Data
public class CourseResource {
    private Long id;
    private String name;
    private String description;
    private List<CompetenceResource> competences;
}
