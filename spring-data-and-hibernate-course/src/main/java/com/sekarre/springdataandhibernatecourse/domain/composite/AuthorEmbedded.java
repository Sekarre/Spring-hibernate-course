package com.sekarre.springdataandhibernatecourse.domain.composite;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "nameId")
@Table(name = "author_composite")
@Entity
public class AuthorEmbedded {

    @EmbeddedId
    private NameId nameId;

    private String country;
}
