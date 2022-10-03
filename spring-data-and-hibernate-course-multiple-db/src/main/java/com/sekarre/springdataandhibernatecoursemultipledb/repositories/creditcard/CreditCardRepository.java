package com.sekarre.springdataandhibernatecoursemultipledb.repositories.creditcard;

import com.sekarre.springdataandhibernatecoursemultipledb.domain.creditcard.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
