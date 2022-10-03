package com.sekarre.springdataandhibernatecourselegacydb.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.sql.Timestamp;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@Entity
@Table(name = "wp_users", indexes = {
        @Index(name = "user_login_key", columnList = "user_login"),
        @Index(name = "user_nicename", columnList = "user_nicename"),
        @Index(name = "user_email", columnList = "user_email"),
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 60)
    @Column(name = "user_login", length = 60)
    private String login;

    @NotNull
    @Size(max = 255)
    @Column(name = "user_pass")
    private String password;

    @NotNull
    @Size(max = 50)
    @Column(name = "user_nicename", length = 50)
    private String nicename;

    @Email
    @NotNull
    @Size(max = 100)
    @Column(name = "user_email", length = 100)
    private String email;

    @URL
    @NotNull
    @Size(max = 100)
    @Column(name = "user_url", length = 50)
    private String url;

    @NotNull
    @Column(name = "user_registered")
    private Timestamp registered;

    @NotNull
    @Size(max = 255)
    @Column(name = "user_activation_key")
    private String activationKey;

    @NotNull
    @Size(max = 11)
    @Column(name = "user_status", length = 11)
    private Integer status;

    @NotNull
    @Size(max = 250)
    @Column(name = "display_name", nullable = false, length = 250)
    private String displayName;

    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<UserMeta> userMetaSet;
}
