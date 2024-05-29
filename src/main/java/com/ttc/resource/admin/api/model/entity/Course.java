package com.ttc.resource.admin.api.model.entity;

import com.ttc.resource.shared.domain.model.AuditModel;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Data
@Table(name = "courses")
public class Course extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 300)
    private String name;

    @NotNull
    @NotBlank
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    /*@OneToMany( mappedBy = "course", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    List<Topic> topics;

    @ManyToMany
    @JoinTable(name="competence_courses", joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "competence_id"))
    private List<Competence> competences;*/
}
