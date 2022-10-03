package com.sekarre.springdataandhibernatecoursemultipledb.services;

import com.sekarre.springdataandhibernatecoursemultipledb.domain.cardholder.CreditCardHolder;
import com.sekarre.springdataandhibernatecoursemultipledb.domain.creditcard.CreditCard;
import com.sekarre.springdataandhibernatecoursemultipledb.domain.pan.CreditCardPAN;
import com.sekarre.springdataandhibernatecoursemultipledb.repositories.cardholder.CreditCardHolderRepository;
import com.sekarre.springdataandhibernatecoursemultipledb.repositories.creditcard.CreditCardRepository;
import com.sekarre.springdataandhibernatecoursemultipledb.repositories.pan.CreditCardPANRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final CreditCardHolderRepository creditCardHolderRepository;
    private final CreditCardPANRepository creditCardPANRepository;

    @Transactional
    @Override
    public CreditCard getCreditCardById(Long id) {
        CreditCard creditCard = creditCardRepository.findById(id).orElseThrow();
        CreditCardHolder creditCardHolder = creditCardHolderRepository.findByCreditCardId(id).orElseThrow();
        CreditCardPAN creditCardPAN = creditCardPANRepository.findByCreditCardId(id).orElseThrow();

        return creditCard.toBuilder()
                .firstName(creditCardHolder.getFirstName())
                .lastName(creditCardHolder.getLastName())
                .creditCardNumber(creditCardPAN.getCreditCardNumber())
                .build();
    }

    @Transactional
    @Override
    public CreditCard saveCreditCard(CreditCard creditCard) {
        CreditCard savedCC = creditCardRepository.save(creditCard);
        savedCC.setFirstName(creditCard.getFirstName());
        savedCC.setLastName(creditCard.getLastName());
        savedCC.setZipCode(creditCard.getZipCode());
        savedCC.setCreditCardNumber(creditCard.getCreditCardNumber());

        creditCardHolderRepository.save(CreditCardHolder.builder()
                .creditCardId(savedCC.getId())
                .firstName(savedCC.getFirstName())
                .lastName(savedCC.getLastName())
                .zipCode(savedCC.getZipCode())
                .build());

        creditCardPANRepository.save(CreditCardPAN.builder()
                .creditCardId(savedCC.getId())
                .creditCardNumber(savedCC.getCreditCardNumber())
                .build());

        return savedCC;
    }
}
