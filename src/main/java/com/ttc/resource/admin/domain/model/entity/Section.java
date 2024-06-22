package com.ttc.resource.admin.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ttc.resource.shared.domain.model.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Data
@Table(name = "sections")
public class Section extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 300)
    private String code;

    @NotNull
    @NotBlank
    @Size(max = 300)
    private String name;

    @NotNull
    @NotBlank
    @Lob
    @Size(max = 300)
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "grade_id", nullable = false)
    @JsonBackReference
    private Grade grade;

    @OneToMany( mappedBy = "section", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    List<SectionDetail> details;

    @OneToMany( mappedBy = "section", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    List<Lesson> lessons;
}
