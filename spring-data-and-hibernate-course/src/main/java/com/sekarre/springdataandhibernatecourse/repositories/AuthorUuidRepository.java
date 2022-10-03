package com.sekarre.springdataandhibernatecourse.repositories;

import com.sekarre.springdataandhibernatecourse.domain.AuthorUuid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorUuidRepository extends JpaRepository<AuthorUuid, UUID> {
}
