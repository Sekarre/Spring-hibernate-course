package com.sekarre.springdataandhibernatecoursemultipledb.domain.pan;

import com.sekarre.springdataandhibernatecoursemultipledb.domain.CreditCardConverter;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "credit_card_pan")
public class CreditCardPAN {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = CreditCardConverter.class)
    private String creditCardNumber;

    private Long creditCardId;
}
