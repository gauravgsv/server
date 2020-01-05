package com.citylive.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @JsonIgnore
    private Integer userId;
    @NotNull
    private String userName;
    @NotNull
    private String firstName;
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String password;
}
