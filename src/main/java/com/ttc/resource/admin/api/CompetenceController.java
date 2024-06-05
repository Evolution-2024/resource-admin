package com.ttc.resource.admin.api;

import com.ttc.resource.admin.domain.service.CompetenceService;
import com.ttc.resource.admin.mapping.CompetenceMapper;
import com.ttc.resource.admin.resource.competence.CompetenceResource;
import com.ttc.resource.admin.resource.competence.CreateCompetenceResource;
import com.ttc.resource.shared.domain.constants.ConstantsService;
import com.ttc.resource.shared.domain.constants.DefaultParams;
import com.ttc.resource.shared.domain.service.communication.BaseResponse;
import com.ttc.resource.shared.exception.ResourceNotFoundException;
import com.ttc.resource.shared.exception.ResourceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/competences")
public class CompetenceController {
    @Autowired
    CompetenceService competenceService;
    @Autowired
    CompetenceMapper mapper;

    @GetMapping
    public ResponseEntity<BaseResponse<List<CompetenceResource>>> getAllCompetences(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) Long id,
            @RequestParam(defaultValue = DefaultParams.PAGE) String page,
            @RequestParam(defaultValue = DefaultParams.SIZE) String size
    ) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("filter", filter);
        parameters.put("id", id);
        parameters.put(ConstantsService.PAGE, page);
        parameters.put(ConstantsService.SIZE, size);
        BaseResponse<List<CompetenceResource>> response = null;
        try {
            List<CompetenceResource> list = mapper.modelListToResource(competenceService.getByFilter(parameters));
            response = new BaseResponse<>(list);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response = new BaseResponse<>(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping
    public ResponseEntity<BaseResponse<CompetenceResource>> createCompetence(@RequestBody CreateCompetenceResource request) {

//        return mapper.toResource(competenceService.create(mapper.toModel(request)));
        BaseResponse<CompetenceResource> response = null;
        try {
            CompetenceResource resource = mapper.toResource(competenceService.create(mapper.toModel(request)));
            response = new BaseResponse<>(resource);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceValidationException | ResourceNotFoundException e) {
            response = new BaseResponse<>(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
