package com.ttc.resource.admin.mapping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("adminMappingConfiguration")
public class MappingConfiguration {

    @Bean
    public CourseMapper courseMapper() { return new CourseMapper(); }

    @Bean
    public GradeMapper gradeMapper() { return new GradeMapper(); }
}
