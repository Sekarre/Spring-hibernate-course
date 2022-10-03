package com.sekarre.springdataandhibernatecoursemapping.repositories;

import com.sekarre.springdataandhibernatecoursemapping.domain.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {
}
