package com.sekarre.springdataandhibernatecoursemultipledb.repositories.pan;

import com.sekarre.springdataandhibernatecoursemultipledb.domain.pan.CreditCardPAN;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditCardPANRepository extends JpaRepository<CreditCardPAN, Long> {
    Optional<CreditCardPAN> findByCreditCardId(Long id);
}
