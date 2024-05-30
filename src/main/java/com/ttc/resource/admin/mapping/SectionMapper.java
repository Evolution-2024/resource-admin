package com.ttc.resource.admin.mapping;

import com.ttc.resource.admin.domain.model.entity.Section;
import com.ttc.resource.admin.resource.section.CreateSectionResource;
import com.ttc.resource.admin.resource.section.SectionResource;
import com.ttc.resource.admin.resource.section.UpdateSectionResource;
import com.ttc.resource.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class SectionMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;

    public SectionResource toResource(Section model) {
        return mapper.map(model, SectionResource.class);
    }

    public Page<SectionResource> modelListToPage(List<Section> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, SectionResource.class), pageable, modelList.size());
    }

    public List<SectionResource> modelListToResource(List<Section> modelList) {
        return mapper.mapList(modelList, SectionResource.class);
    }

    public Section toModel(CreateSectionResource resource) {
        return mapper.map(resource, Section.class);
    }

    public Section toModel(UpdateSectionResource resource) {
        return mapper.map(resource, Section.class);
    }
}
