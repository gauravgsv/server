package com.citylive.server.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`User`")
public class User {
    @NotNull
    @Id
    @Column(name = "`UserName`")
    private String userName;
    @NotNull
    @Column(name = "`FirstName`")
    private String firstName;
    @Column(name = "`LastName`")
    private String lastName;
    @NotNull
    @Column(name = "`Email`")
    private String email;
    @NotNull
    @Column(name = "`Password`")
    private String password;
    @Column(name = "`Role`")
    private String role = "USER";
}
