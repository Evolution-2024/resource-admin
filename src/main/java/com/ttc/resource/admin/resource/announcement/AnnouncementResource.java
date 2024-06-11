package com.ttc.resource.admin.resource.announcement;

import lombok.Data;

@Data
public class AnnouncementResource {
    private Long id;
    private String title;
    private String description;
    private Long teacherId;
    private Long sectionId;
}
