package com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.repositories;

import com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.domain.BookSpringData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface BookRepository extends JpaRepository<BookSpringData, Long> {

    BookSpringData bookByTitleJpaNamed(@Param("title") String title);

    @Query(value = "select * from book where title = :title", nativeQuery = true)
    BookSpringData findBookByTitleNativeQuery(@Param("title") String title);

    @Query("select b from BookSpringData b where b.title = :title")
    BookSpringData findBookByTitleWithQueryNamed(@Param("title") String title);

    @Query("select b from BookSpringData b where b.title = ?1")
    BookSpringData findBookByTitleWithQuery(String title);

    Optional<BookSpringData> findByTitle(String title);

    @Nullable
    BookSpringData getByTitle(@Nullable String title);

    Stream<BookSpringData> findAllByTitleNotNull();

    @Async
    Future<BookSpringData> queryByTitle(String title);
}
