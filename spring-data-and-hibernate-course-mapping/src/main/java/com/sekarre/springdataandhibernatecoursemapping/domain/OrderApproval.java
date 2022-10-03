package com.sekarre.springdataandhibernatecoursemapping.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
public class OrderApproval extends BaseEntity {

    private String approved_by;

    @OneToOne
    @JoinColumn(name = "order_header_id")
    private OrderHeader orderHeader;
}
