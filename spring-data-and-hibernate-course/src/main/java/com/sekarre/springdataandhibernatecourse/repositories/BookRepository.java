package com.sekarre.springdataandhibernatecourse.repositories;

import com.sekarre.springdataandhibernatecourse.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
