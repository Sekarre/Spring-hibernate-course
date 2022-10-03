package com.sekarre.springdataandhibernatecoursemapping.bootloader;

import com.sekarre.springdataandhibernatecoursemapping.domain.Customer;
import com.sekarre.springdataandhibernatecoursemapping.domain.Product;
import com.sekarre.springdataandhibernatecoursemapping.domain.ProductStatus;
import com.sekarre.springdataandhibernatecoursemapping.repositories.CustomerRepository;
import com.sekarre.springdataandhibernatecoursemapping.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class Bootloader implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final ProductService productService;

    @Override
    public void run(String... args) {
        updateProduct();
        versionAndLockingExample();
    }

    private void versionAndLockingExample() {
        Customer customer = Customer.builder().customerName("Testing version").build();
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Version is: " + savedCustomer.getVersion());

        savedCustomer.setCustomerName("Testing version 2");
        Customer savedCustomer2 = customerRepository.save(savedCustomer);
        log.info("Version is: " + savedCustomer2.getVersion());

        savedCustomer2.setCustomerName("Testing version 3");
//        Customer savedCustomer3 = customerRepository.save(savedCustomer); // this fails cuz of optimistic locking - it has been already updated in current session
        Customer savedCustomer3 = customerRepository.save(savedCustomer2);
        log.info("Version is: " + savedCustomer3.getVersion());

//        customerRepository.delete(savedCustomer); //this fails cuz of stale object - not proper version
        customerRepository.delete(savedCustomer3);
    }

    void updateProduct() {
        Product product = Product.builder().description("Desc").productStatus(ProductStatus.DISCONTINUED).build();
        Product saved = productService.savedProduct(product);

        Product saved2 = productService.updateQQH(saved.getId(), 25);

        log.info("Updated Qty: " + saved2.getQuantityOnHand());
    }
}
