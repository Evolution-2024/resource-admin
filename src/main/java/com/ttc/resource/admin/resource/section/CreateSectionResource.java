package com.ttc.resource.admin.resource.section;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateSectionResource {
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
    @Size(max = 300)
    private String description;
}
