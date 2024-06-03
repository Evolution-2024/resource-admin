package com.ttc.resource.admin.api;

import com.ttc.resource.admin.domain.service.SectionService;
import com.ttc.resource.admin.mapping.SectionMapper;
import com.ttc.resource.admin.resource.course.CourseResource;
import com.ttc.resource.admin.resource.grade.GradeResource;
import com.ttc.resource.admin.resource.section.CreateSectionResource;
import com.ttc.resource.admin.resource.section.SectionResource;
import com.ttc.resource.admin.resource.section.UpdateSectionResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/sections")
public class SectionController {
    @Autowired
    private SectionService sectionService;
    @Autowired
    private SectionMapper mapper;

    @GetMapping
    public Page<SectionResource> getAll(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long gradeId,
            Pageable pageable
    ) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("filter", filter);
        parameters.put("id", id);
        parameters.put("gradeId", gradeId);
        return mapper.modelListToPage(sectionService.getByFilter(parameters), pageable);
    }

    @PostMapping
    public SectionResource postSection(@RequestBody CreateSectionResource resource) {
        return mapper.toResource(sectionService.create(resource.getGradeId(), mapper.toModel(resource)));
    }

    @PutMapping("{sectionId}")
    public SectionResource putSection(@PathVariable Long sectionId, @RequestBody UpdateSectionResource resource) {
        return mapper.toResource(sectionService.update(sectionId, mapper.toModel(resource)));
    }

    @DeleteMapping("{sectionId}")
    public ResponseEntity<?> deleteSection(@PathVariable Long sectionId) {
        return sectionService.delete(sectionId);
    }
}
