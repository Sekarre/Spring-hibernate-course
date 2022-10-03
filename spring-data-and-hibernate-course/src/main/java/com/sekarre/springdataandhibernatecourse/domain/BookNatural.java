package com.sekarre.springdataandhibernatecourse.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "title")
@Entity
public class BookNatural {

    @Id
    private String title;

    private String isbn;
    private String publisher;
}
