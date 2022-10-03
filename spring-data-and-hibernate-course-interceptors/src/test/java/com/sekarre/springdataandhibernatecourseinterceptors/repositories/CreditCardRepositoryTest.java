package com.sekarre.springdataandhibernatecourseinterceptors.repositories;

import com.sekarre.springdataandhibernatecourseinterceptors.domain.CreditCard;
import com.sekarre.springdataandhibernatecourseinterceptors.services.EncryptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditCardRepositoryTest {

    final String CREDIT_CARD = "123432003200";

    @Autowired
    EncryptionService encryptionService;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void testSaveAndStoreCreditCard() {
        CreditCard creditCard = CreditCard.builder()
                .creditCardNumber(CREDIT_CARD)
                .cvv("123")
                .expirationDate("12/2029")
                .build();

        CreditCard savedCC = creditCardRepository.saveAndFlush(creditCard);
        Map<String, Object> dbRow = jdbcTemplate.queryForMap("select * from credit_card where id = " + savedCC.getId());
        String dbCardValue = (String) dbRow.get("credit_card_number");
        assertThat(savedCC.getCreditCardNumber()).isNotEqualTo(dbCardValue);
        assertThat(dbCardValue).isEqualTo(encryptionService.encrypt(CREDIT_CARD));


        CreditCard fetchedCC = creditCardRepository.findById(savedCC.getId()).get();
        assertThat(savedCC.getCreditCardNumber()).isEqualTo(fetchedCC.getCreditCardNumber());
    }
}