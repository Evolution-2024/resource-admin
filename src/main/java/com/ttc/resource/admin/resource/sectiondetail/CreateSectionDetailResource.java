package com.ttc.resource.admin.resource.sectiondetail;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateSectionDetailResource {
    private String studentUsername;
    private Long sectionId;
    private Long studentCode;
}
