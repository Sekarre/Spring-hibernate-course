package com.sekarre.springdataandhibernatecoursemapping.services;

import com.sekarre.springdataandhibernatecoursemapping.domain.Product;
import com.sekarre.springdataandhibernatecoursemapping.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product savedProduct(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Transactional
    @Override
    public Product updateQQH(Long id, Integer quantityOnHand) {
        Product product = productRepository.findById(id)
                .orElseThrow();
        product.setQuantityOnHand(quantityOnHand);
        return productRepository.saveAndFlush(product);
    }
}
