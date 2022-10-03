package com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.domain;

import jakarta.persistence.*;
import lombok.*;

@NamedQuery(name = "BookSpringData.bookByTitleJpaNamed", query = "from BookSpringData b where b.title = :title")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "book")
public class BookSpringData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String isbn;
    private String publisher;
    private Long authorId;
}
