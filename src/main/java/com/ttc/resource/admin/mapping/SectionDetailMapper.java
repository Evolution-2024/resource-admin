package com.ttc.resource.admin.mapping;

import com.ttc.resource.admin.domain.model.entity.SectionDetail;
import com.ttc.resource.admin.resource.sectiondetail.CreateSectionDetailResource;
import com.ttc.resource.admin.resource.sectiondetail.SectionDetailResource;
import com.ttc.resource.admin.resource.sectiondetail.UpdateSectionDetailResource;
import com.ttc.resource.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class SectionDetailMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;

    public SectionDetailResource toResource(SectionDetail model) {
        return mapper.map(model, SectionDetailResource.class);
    }

    public Page<SectionDetailResource> modelListToPage(List<SectionDetail> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, SectionDetailResource.class), pageable, modelList.size());
    }

    public List<SectionDetailResource> modelListToResource(List<SectionDetail> modelList) {
        return mapper.mapList(modelList, SectionDetailResource.class);
    }

    public SectionDetail toModel(CreateSectionDetailResource resource) {
        return mapper.map(resource, SectionDetail.class);
    }

    public SectionDetail toModel(UpdateSectionDetailResource resource) {
        return mapper.map(resource, SectionDetail.class);
    }
}
