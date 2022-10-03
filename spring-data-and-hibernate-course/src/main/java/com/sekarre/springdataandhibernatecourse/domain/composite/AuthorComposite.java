package com.sekarre.springdataandhibernatecourse.domain.composite;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"firstName", "lastName"})
@IdClass(NameId.class)
@Entity
public class AuthorComposite {

    @Id
    private String firstName;

    @Id
    private String lastName;

    private String country;
}
