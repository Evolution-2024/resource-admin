package com.ttc.resource.admin.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "grades")
public class Grade extends AuditModel {
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
    @Size(max = 300)
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @OneToMany( mappedBy = "grade", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonManagedReference
    List<Section> sections;
}
