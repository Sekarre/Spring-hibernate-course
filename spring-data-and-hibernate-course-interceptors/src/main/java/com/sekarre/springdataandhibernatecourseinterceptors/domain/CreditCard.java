package com.sekarre.springdataandhibernatecourseinterceptors.domain;

import com.sekarre.springdataandhibernatecourseinterceptors.interceptors.EncryptedString;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
//@EntityListeners(CreditCardJPACallback.class)
@Entity
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @EncryptedString
    @Convert(converter = CreditCardConverter.class)
    private String creditCardNumber;

    private String cvv;

    private String expirationDate;

    @PrePersist
    public void prePersistCallback() {
        System.out.println("JPA prepersist");
    }
}
