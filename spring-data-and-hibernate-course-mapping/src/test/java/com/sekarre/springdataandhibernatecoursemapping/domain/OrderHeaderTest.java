package com.sekarre.springdataandhibernatecoursemapping.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderHeaderTest {

    @Test
    void testEquals() {
        OrderHeader orderHeader1 = OrderHeader.builder().id(1L).build();
        OrderHeader orderHeader2 = OrderHeader.builder().id(1L).build();

        assertEquals(orderHeader1, orderHeader2);
    }

    @Test
    void testNotEquals() {
        OrderHeader orderHeader1 = OrderHeader.builder().id(1L).build();
        OrderHeader orderHeader2 = OrderHeader.builder().id(2L).build();

        assertNotEquals(orderHeader1, orderHeader2);
    }
}