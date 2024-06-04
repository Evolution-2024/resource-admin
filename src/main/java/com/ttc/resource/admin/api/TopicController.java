package com.ttc.resource.admin.api;

import com.ttc.resource.admin.domain.service.TopicService;
import com.ttc.resource.admin.mapping.TopicMapper;
import com.ttc.resource.admin.resource.topic.CreateTopicResource;
import com.ttc.resource.admin.resource.topic.TopicResource;
import com.ttc.resource.shared.domain.constants.ConstantsService;
import com.ttc.resource.shared.domain.constants.DefaultParams;
import com.ttc.resource.shared.domain.service.communication.BaseResponse;
import com.ttc.resource.shared.exception.ResourceNotFoundException;
import com.ttc.resource.shared.exception.ResourceValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
    public ResponseEntity<BaseResponse<List<TopicResource>>> getFilter(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long courseId,
            @RequestParam(defaultValue = DefaultParams.PAGE) String page,
            @RequestParam(defaultValue = DefaultParams.SIZE) String size
    ) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("filter", filter);
        parameters.put("id", id);
        parameters.put("courseId", courseId);
        parameters.put(ConstantsService.PAGE, page);
        parameters.put(ConstantsService.SIZE, size);
        BaseResponse<List<TopicResource>> response = null;
        try {
            List<TopicResource> list = mapper.modelListToResource(topicService.getByFilter(parameters));
            response = new BaseResponse<>(list);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response = new BaseResponse<>(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping
    public ResponseEntity<BaseResponse<TopicResource>> createTopic(@RequestBody CreateTopicResource request) {
        BaseResponse<TopicResource> response = null;
        try {
            TopicResource resource = mapper.toResource(topicService.create(mapper.toModel(request),request.getCourseId()));
            response = new BaseResponse<>(resource);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceValidationException | ResourceNotFoundException e) {
            response = new BaseResponse<>(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
