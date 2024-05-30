package com.ttc.resource.admin.api;

import com.ttc.resource.admin.domain.service.TopicService;
import com.ttc.resource.admin.mapping.TopicMapper;
import com.ttc.resource.admin.resource.course.CourseResource;
import com.ttc.resource.admin.resource.topic.CreateTopicResource;
import com.ttc.resource.admin.resource.topic.TopicResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/topics")
public class TopicController {
    private final TopicService topicService;
    private final TopicMapper mapper;

    public TopicController(TopicService topicService, TopicMapper mapper) {
        this.topicService = topicService;
        this.mapper = mapper;
    }
    @GetMapping
    public Page<TopicResource> getFilter(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long courseId,
            Pageable pageable
    ) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("filter", filter);
        parameters.put("id", id);
        parameters.put("courseId", courseId);
        return mapper.modelListToPage(topicService.getByFilter(parameters), pageable);
    }
    @PostMapping
    public TopicResource createTopic(@RequestBody CreateTopicResource request) {
        return mapper.toResource(topicService.create(mapper.toModel(request),request.getCourseId()));
    }
}
