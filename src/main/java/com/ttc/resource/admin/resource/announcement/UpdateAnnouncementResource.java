package com.ttc.resource.admin.resource.announcement;

import lombok.Data;

@Data
public class UpdateAnnouncementResource {
    private Long id;
    private String title;
    private String description;
}
