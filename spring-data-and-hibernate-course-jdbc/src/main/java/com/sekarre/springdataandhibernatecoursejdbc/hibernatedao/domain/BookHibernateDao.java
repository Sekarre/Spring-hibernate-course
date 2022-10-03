package com.sekarre.springdataandhibernatecoursejdbc.hibernatedao.domain;

import jakarta.persistence.*;
import lombok.*;

import static com.sekarre.springdataandhibernatecoursejdbc.hibernatedao.dao.HibernateBookDaoImpl.BOOK_BY_TITLE_QUERY_NAME;
import static com.sekarre.springdataandhibernatecoursejdbc.hibernatedao.dao.HibernateBookDaoImpl.BOOK_FIND_ALL_QUERY_NAME;

@NamedQueries({
        @NamedQuery(name = BOOK_FIND_ALL_QUERY_NAME, query = "from BookHibernateDao"),
        @NamedQuery(name = BOOK_BY_TITLE_QUERY_NAME, query = "from BookHibernateDao b where b.title = :title")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "book")
public class BookHibernateDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String isbn;
    private String publisher;
    private Long authorId;
}
