package com.sekarre.springdataandhibernatecoursemultipledb.repositories.cardholder;

import com.sekarre.springdataandhibernatecoursemultipledb.domain.cardholder.CreditCardHolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CreditCardHolderRepository extends JpaRepository<CreditCardHolder, Long> {
    Optional<CreditCardHolder> findByCreditCardId(Long id);
}
