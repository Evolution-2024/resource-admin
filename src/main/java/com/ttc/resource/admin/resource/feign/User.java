package com.ttc.resource.admin.resource.feign;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@With
public class User {
    private Long id;
    private String username;
    private String email;
    private List<Role> roles;
}