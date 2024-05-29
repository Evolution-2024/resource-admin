package com.ttc.resource.admin.mapping;

import com.ttc.resource.admin.api.model.entity.Grade;
import com.ttc.resource.admin.resource.grade.GradeResource;
import com.ttc.resource.admin.resource.grade.CreateGradeResource;
import com.ttc.resource.admin.resource.grade.UpdateGradeResource;
import com.ttc.resource.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class GradeMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;

    public GradeResource toResource(Grade model) {
        return mapper.map(model, GradeResource.class);
    }

    public Page<GradeResource> modelListToPage(List<Grade> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, GradeResource.class), pageable, modelList.size());
    }

    public List<GradeResource> modelListToResource(List<Grade> modelList) {
        return mapper.mapList(modelList, GradeResource.class);
    }

    public Grade toModel(CreateGradeResource resource) {
        return mapper.map(resource, Grade.class);
    }

    public Grade toModel(UpdateGradeResource resource) {
        return mapper.map(resource, Grade.class);
    }
}
