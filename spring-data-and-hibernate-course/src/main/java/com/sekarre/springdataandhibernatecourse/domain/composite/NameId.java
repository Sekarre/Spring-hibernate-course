package com.sekarre.springdataandhibernatecourse.domain.composite;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"firstName", "lastName"})
@Embeddable
public class NameId implements Serializable {

    private String firstName;
    private String lastName;
}
