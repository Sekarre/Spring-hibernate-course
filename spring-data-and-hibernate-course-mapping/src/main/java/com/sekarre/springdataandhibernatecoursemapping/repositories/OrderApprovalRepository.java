package com.sekarre.springdataandhibernatecoursemapping.repositories;

import com.sekarre.springdataandhibernatecoursemapping.domain.OrderApproval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderApprovalRepository extends JpaRepository<OrderApproval, Long> {
}
