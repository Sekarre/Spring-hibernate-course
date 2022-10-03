package com.sekarre.springdataandhibernatecoursemapping.repositories;

import com.sekarre.springdataandhibernatecoursemapping.domain.OrderHeader;
import com.sekarre.springdataandhibernatecoursemapping.domain.Product;
import com.sekarre.springdataandhibernatecoursemapping.domain.ProductStatus;
import com.sekarre.springdataandhibernatecoursemapping.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackageClasses = {ProductService.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Test
    void testGetCategory() {
        Product product = productRepository.findByDescription("PRODUCT1").get();

        assertNotNull(product);
        assertNotNull(product.getCategories());
    }

    @Test
    void testSaveProduct() {
        Product product = Product.builder().description("Desc").productStatus(ProductStatus.DISCONTINUED).build();
        Product saved = productRepository.save(product);

        assertNotNull(saved);
        assertNotNull(saved.getId());

        Product fetched = productRepository.getReferenceById(saved.getId());
        assertNotNull(fetched);
        assertNotNull(fetched.getId());
        assertNotNull(fetched.getCreatedDate());
        assertNotNull(fetched.getLastModifiedDate());
        assertNotNull(fetched.getProductStatus());
    }

    @Test
    void addAndUpdateProduct() {
        Product product = Product.builder().description("Desc").productStatus(ProductStatus.DISCONTINUED).build();
        Product saved = productService.savedProduct(product);

        Product saved2 = productService.updateQQH(saved.getId(), 25);

        assertNotNull(saved2);
        assertEquals(saved2.getQuantityOnHand(), 25);
    }
}