package com.sekarre.springdataandhibernatecoursemapping.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Embeddable
public class Address {

    @Size(max = 30)
    private String address;

    @Size(max = 30)
    private String city;

    @Size(max = 30)
    private String state;

    @Size(max = 30)
    private String zipCode;
}
