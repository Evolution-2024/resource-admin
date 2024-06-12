package com.ttc.resource.admin.domain.model.entity;

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
@Table(name = "topics")
public class Topic extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 300)
    private String title;

    @NotNull
    @NotBlank
    @Size(max = 300)
    private String description;

    private String file;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] file_data;

    //relation with courses
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}
