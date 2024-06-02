package com.ttc.resource.admin.api;

import com.ttc.resource.admin.domain.model.entity.Grade;
import com.ttc.resource.admin.domain.service.GradeService;
import com.ttc.resource.admin.mapping.GradeMapper;
import com.ttc.resource.admin.resource.grade.CreateGradeResource;
import com.ttc.resource.admin.resource.grade.GradeResource;
import com.ttc.resource.admin.resource.grade.UpdateGradeResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/grades")
public class GradeController {
    @Autowired
    private GradeService gradeService;
    @Autowired
    private GradeMapper mapper;

    @GetMapping()
    public Page<GradeResource> getAll(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) Long id,
            Pageable pageable
    ) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("filter", filter);
        parameters.put("id", id);
        return mapper.modelListToPage(gradeService.getByFilter(parameters), pageable);
    }

    @PostMapping
    public Grade postGrade(@RequestBody CreateGradeResource resource) {
        return gradeService.create(mapper.toModel(resource));
    }
//
//    @PutMapping("{gradeId}")
//    public Grade putGrade(@PathVariable Long gradeId, @RequestBody UpdateGradeResource resource) {
//        return gradeService.update(gradeId, mapper.toModel(resource));
//    }
//
//    @DeleteMapping("{gradeId}")
//    public ResponseEntity<?> deleteGrade(@PathVariable Long gradeId) {
//        return gradeService.delete(gradeId);
//    }
}
