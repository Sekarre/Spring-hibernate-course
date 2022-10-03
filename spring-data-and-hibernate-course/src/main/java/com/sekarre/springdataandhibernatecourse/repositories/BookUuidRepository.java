package com.sekarre.springdataandhibernatecourse.repositories;

import com.sekarre.springdataandhibernatecourse.domain.BookUuid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookUuidRepository extends JpaRepository<BookUuid, UUID> {
}
