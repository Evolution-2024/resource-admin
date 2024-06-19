package com.ttc.resource.admin.resource.topic;

import lombok.Data;

@Data
public class TopicResource {
    private Long id;
    private String title;
    private String description;
    private String file;
    private String content;
    private Long courseId;
}
