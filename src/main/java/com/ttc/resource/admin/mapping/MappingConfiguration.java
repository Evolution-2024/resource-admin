package com.ttc.resource.admin.mapping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("adminMappingConfiguration")
public class MappingConfiguration {

    @Bean
    public CourseMapper courseMapper() { return new CourseMapper(); }
    @Bean
    public GradeMapper gradeMapper() { return new GradeMapper(); }
    @Bean
    public SectionMapper sectionMapper() { return new SectionMapper(); }
    @Bean
    public SectionDetailMapper sectionDetailMapper() { return new SectionDetailMapper(); }
    @Bean
    public AnnouncementMapper announcementMapper() { return new AnnouncementMapper(); }
    @Bean
    public TopicMapper topicMapper() { return new TopicMapper(); }
    @Bean
    public CompetenceMapper competenceMapper() { return new CompetenceMapper(); }
    @Bean
    public LessonMapper lessonMapper() { return new LessonMapper(); }
}
