package com.ttc.resource.admin.domain.model.entity;

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

@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Data
@Table(name = "announcements")
public class Announcement extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 300)
    private String title;

    @NotNull
    @NotBlank
    @Lob
    @Size(max = 300)
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    private Long studentId;
    private Long sectionId;
}