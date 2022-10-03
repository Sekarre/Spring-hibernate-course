package com.sekarre.springdataandhibernatecoursemapping.services;

import com.sekarre.springdataandhibernatecoursemapping.domain.Product;

public interface ProductService {

    Product savedProduct(Product product);

    Product updateQQH(Long id, Integer quantityOnHand);
}
