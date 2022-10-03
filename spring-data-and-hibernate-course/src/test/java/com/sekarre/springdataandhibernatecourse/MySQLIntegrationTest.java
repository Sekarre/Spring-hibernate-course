package com.sekarre.springdataandhibernatecourse;

import com.sekarre.springdataandhibernatecourse.domain.AuthorUuid;
import com.sekarre.springdataandhibernatecourse.domain.BookNatural;
import com.sekarre.springdataandhibernatecourse.domain.BookUuid;
import com.sekarre.springdataandhibernatecourse.domain.composite.AuthorComposite;
import com.sekarre.springdataandhibernatecourse.domain.composite.AuthorEmbedded;
import com.sekarre.springdataandhibernatecourse.domain.composite.NameId;
import com.sekarre.springdataandhibernatecourse.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.sekarre.springdataandhibernatecourse.bootloader"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MySQLIntegrationTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorUuidRepository authorUuidRepository;

    @Autowired
    BookUuidRepository bookUuidRepository;

    @Autowired
    BookNaturalRepository bookNaturalRepository;

    @Autowired
    AuthorCompositeRepository authorCompositeRepository;

    @Autowired
    AuthorEmbeddedRepository authorEmbeddedRepository;

    @Test
    void testAuthorEmbedded() {
        NameId nameId = NameId.builder().firstName("S").lastName("K").build();
        AuthorEmbedded authorEmbedded = AuthorEmbedded.builder().nameId(nameId).build();
        AuthorEmbedded saved = authorEmbeddedRepository.save(authorEmbedded);
        assertThat(saved).isNotNull();

        AuthorEmbedded fetched = authorEmbeddedRepository.getReferenceById(nameId);
        assertThat(fetched).isNotNull();
    }

    @Test
    void testAuthorComposite() {
        NameId nameId = NameId.builder().firstName("S").lastName("K").build();
        AuthorComposite authorComposite = AuthorComposite.builder()
                .firstName(nameId.getFirstName())
                .lastName(nameId.getLastName())
                .build();
        AuthorComposite saved = authorCompositeRepository.save(authorComposite);
        assertThat(saved).isNotNull();

        AuthorComposite fetched = authorCompositeRepository.getReferenceById(nameId);
        assertThat(fetched).isNotNull();
    }

    @Test
    void testBookNatural() {
        BookNatural bookNatural = BookNatural.builder().title("My book").build();
        BookNatural saved = bookNaturalRepository.save(bookNatural);

        BookNatural fetched = bookNaturalRepository.getReferenceById(saved.getTitle());
        assertThat(fetched).isNotNull();
    }

    @Test
    void testBookUuid() {
        BookUuid bookUuid = bookUuidRepository.save(BookUuid.builder().build());

        assertThat(bookUuid).isNotNull();
        assertThat(bookUuid.getId()).isNotNull();

        BookUuid fetched = bookUuidRepository.getReferenceById(bookUuid.getId());
        assertThat(fetched).isNotNull();
    }

    @Test
    void testAuthorUuid() {
        AuthorUuid authorUuid = authorUuidRepository.save(AuthorUuid.builder().build());

        assertThat(authorUuid).isNotNull();
        assertThat(authorUuid.getId()).isNotNull();

        AuthorUuid fetched = authorUuidRepository.getReferenceById(authorUuid.getId());
        assertThat(fetched).isNotNull();
    }

    @Test
    void testMySQL() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);
    }
}
