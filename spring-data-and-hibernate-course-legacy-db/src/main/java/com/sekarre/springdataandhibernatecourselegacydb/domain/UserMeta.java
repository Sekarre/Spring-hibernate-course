package com.sekarre.springdataandhibernatecourselegacydb.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@Entity
@Table(name = "wp_usermeta")
public class UserMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "umeta_id")
    private Long id;

    //    private Long userId;
    @ManyToOne
    private User user;

    @Size(max = 255)
    private String metaKey;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String metaValue;
}
