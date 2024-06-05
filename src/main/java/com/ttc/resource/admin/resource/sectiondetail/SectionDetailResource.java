package com.ttc.resource.admin.resource.sectiondetail;

import com.ttc.resource.admin.resource.section.SectionResource;
import lombok.Data;

@Data
public class SectionDetailResource {
    private Long id;
    private String studentUsername;
    private SectionResource section;
    private Long studentId;
}
