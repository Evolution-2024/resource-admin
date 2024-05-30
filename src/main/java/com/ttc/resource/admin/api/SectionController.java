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

    @GetMapping()
    public Page<SectionResource> getAll(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) Long id,
            Pageable pageable
    ) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("filter", filter);
        parameters.put("id", id);
        return mapper.modelListToPage(sectionService.getByFilter(parameters), pageable);
    }

    @PostMapping("section/{gradeId}")
    public SectionResource postSection(@PathVariable Long gradeId, @RequestBody CreateSectionResource resource) {
        return mapper.toResource(sectionService.create(gradeId, mapper.toModel(resource)));
    }

    @PutMapping("{sectionId}")
    public SectionResource putSection(@PathVariable Long sectionId, @RequestBody UpdateSectionResource resource) {
        return mapper.toResource(sectionService.update(sectionId, mapper.toModel(resource)));
    }

    @DeleteMapping("{gradeId}")
    public ResponseEntity<?> deleteSection(@PathVariable Long gradeId) {
        return sectionService.delete(gradeId);
    }
}
