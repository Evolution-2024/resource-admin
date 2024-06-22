package com.ttc.resource.admin.mapping;

import com.ttc.resource.admin.domain.model.entity.Lesson;
import com.ttc.resource.admin.resource.lesson.CreateLessonResource;
import com.ttc.resource.admin.resource.lesson.LessonResource;
import com.ttc.resource.shared.mapping.EnhancedModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class LessonMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;

    public LessonResource toResource(Lesson model) {
        return mapper.map(model, LessonResource.class);
    }

    public Page<LessonResource> modelListToPage(List<Lesson> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, LessonResource.class), pageable, modelList.size());
    }

    public List<LessonResource> modelListToResource(List<Lesson> modelList) {
        return mapper.mapList(modelList, LessonResource.class);
    }

    public Lesson toModel(CreateLessonResource resource) {
        /*mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.addMappings(new PropertyMap<CreateLessonResource, Lesson>() {
            @Override
            protected void configure() {
                map().setTeacherUsername(source.getTeacherUsername());
                map().setTeacherCode(source.getTeacherCode());

//                skip(destination.getId());
//                skip(destination.getSection());
//                skip(destination.getCourse());
            }
        });*/
        return mapper.map(resource, Lesson.class);
    }
}
