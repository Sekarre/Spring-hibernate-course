package com.sekarre.springdataandhibernatecoursemapping.repositories;

import com.sekarre.springdataandhibernatecoursemapping.domain.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderApprovalRepository orderApprovalRepository;

    Product product;

    @BeforeEach
    void setUp() {
       product = productRepository.saveAndFlush(Product.builder()
                .productStatus(ProductStatus.NEW)
                .description("Test")
                .build());
    }

    @Test
    void testSaveOrderWithLine() {
        OrderHeader orderHeader = OrderHeader.builder().build();

        Customer customer = Customer.builder().customerName("New customer").build();
        Customer savedCustomer = customerRepository.save(customer);
        orderHeader.setCustomer(savedCustomer);

        OrderLine orderLine = OrderLine.builder().quantityOrdered(5).build();
        orderLine.setProduct(product);
        orderHeader.addOrderLine(orderLine);

        OrderApproval orderApproval = OrderApproval.builder().approved_by("mm").build();
        orderHeader.setOrderApproval(orderApproval);

        OrderHeader saved = orderHeaderRepository.save(orderHeader);

        assertNotNull(saved);
        assertNotNull(saved.getId());

        OrderHeader fetched = orderHeaderRepository.getReferenceById(saved.getId());
        assertNotNull(fetched);
        assertNotNull(fetched.getId());
        assertNotNull(fetched.getCreatedDate());
        assertNotNull(fetched.getLastModifiedDate());
        assertNotNull(fetched.getOrderLines());
        assertEquals(saved.getOrderLines().size(), 1);
    }

    @Test
    void testSaveOrder() {
        OrderHeader orderHeader = OrderHeader.builder().build();
        Customer customer = Customer.builder().customerName("New customer").build();
        Customer savedCustomer = customerRepository.save(customer);
        orderHeader.setCustomer(savedCustomer);

        OrderHeader saved = orderHeaderRepository.save(orderHeader);

        assertNotNull(saved);
        assertNotNull(saved.getId());

        OrderHeader fetched = orderHeaderRepository.getReferenceById(saved.getId());
        assertNotNull(fetched);
        assertNotNull(fetched.getId());
        assertNotNull(fetched.getCreatedDate());
        assertNotNull(fetched.getLastModifiedDate());
    }

    @Test
    void testDeleteCascade() {
        OrderHeader orderHeader = OrderHeader.builder().build();

        Customer customer = Customer.builder().customerName("New customer").build();
        Customer savedCustomer = customerRepository.save(customer);
        orderHeader.setCustomer(savedCustomer);

        OrderLine orderLine = OrderLine.builder().quantityOrdered(5).build();
        orderLine.setProduct(product);
        orderHeader.addOrderLine(orderLine);

        OrderApproval orderApproval = OrderApproval.builder().approved_by("mm").build();
        orderHeader.setOrderApproval(orderApproval);

        OrderHeader saved = orderHeaderRepository.saveAndFlush(orderHeader);

        orderHeaderRepository.deleteById(saved.getId());
        orderHeaderRepository.flush();

        assertThrows(EntityNotFoundException.class, () -> {
            OrderHeader fetchedOrder = orderHeaderRepository.getReferenceById(saved.getId());
            assertNull(fetchedOrder);
        });
    }
}