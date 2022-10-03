package com.sekarre.springdataandhibernatecourse.repositories;

import com.sekarre.springdataandhibernatecourse.domain.composite.AuthorEmbedded;
import com.sekarre.springdataandhibernatecourse.domain.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorEmbeddedRepository extends JpaRepository<AuthorEmbedded, NameId> {
}
