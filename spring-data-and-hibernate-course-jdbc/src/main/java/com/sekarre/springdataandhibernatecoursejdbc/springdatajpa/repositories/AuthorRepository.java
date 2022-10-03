package com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.repositories;

import com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.domain.AuthorSpringData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<AuthorSpringData, Long> {

    Optional<AuthorSpringData> findByFirstNameAndLastName(String firstName, String lastName);

    Page<AuthorSpringData> findAllByLastName(String lastName, Pageable pageable);
}
