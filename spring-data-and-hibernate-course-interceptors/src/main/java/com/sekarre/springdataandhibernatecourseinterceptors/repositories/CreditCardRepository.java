package com.sekarre.springdataandhibernatecourseinterceptors.repositories;

import com.sekarre.springdataandhibernatecourseinterceptors.domain.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
