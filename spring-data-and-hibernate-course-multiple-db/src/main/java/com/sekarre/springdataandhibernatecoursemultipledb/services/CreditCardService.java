package com.sekarre.springdataandhibernatecoursemultipledb.services;


import com.sekarre.springdataandhibernatecoursemultipledb.domain.creditcard.CreditCard;

public interface CreditCardService {

    CreditCard getCreditCardById(Long id);

    CreditCard saveCreditCard(CreditCard creditCard);
}
