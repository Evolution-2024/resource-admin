package com.ttc.resource.admin.resource.section;

import lombok.Data;

@Data
public class SectionResource {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Long gradeId;
}
