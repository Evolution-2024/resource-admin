package com.ttc.resource.admin.resource.announcement;

import lombok.Data;

@Data
public class CreateAnnouncementResource {
    private String title;
    private String description;
    private Long sectionCode;
    private Long studentCode;
}
