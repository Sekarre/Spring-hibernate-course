package com.sekarre.springdataandhibernatecourse.repositories;

import com.sekarre.springdataandhibernatecourse.domain.composite.AuthorComposite;
import com.sekarre.springdataandhibernatecourse.domain.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorCompositeRepository extends JpaRepository<AuthorComposite, NameId> {
}
