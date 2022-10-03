package com.sekarre.springdataandhibernatecourse.bootloader;

import com.sekarre.springdataandhibernatecourse.domain.AuthorUuid;
import com.sekarre.springdataandhibernatecourse.domain.Book;
import com.sekarre.springdataandhibernatecourse.domain.BookUuid;
import com.sekarre.springdataandhibernatecourse.repositories.AuthorUuidRepository;
import com.sekarre.springdataandhibernatecourse.repositories.BookRepository;
import com.sekarre.springdataandhibernatecourse.repositories.BookUuidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"local", "default"})
@RequiredArgsConstructor
@Slf4j
@Component
public class Bootloader implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorUuidRepository authorUuidRepository;
    private final BookUuidRepository bookUuidRepository;

    @Override
    public void run(String... args) {
        createBooks();
        createAuthorUuids();
        createBookUuids();
    }

    private void createBooks() {
        if (bookRepository.count() == 0) {
            bookRepository.save(Book.builder()
                    .isbn("1111")
                    .title("Spring in Action")
                    .publisher("Orily")
                    .build());
            bookRepository.save(Book.builder()
                    .isbn("2222")
                    .title("Domain Driven design")
                    .publisher("RandomHouse")
                    .build());
        }
    }

    private void createAuthorUuids() {
        if (authorUuidRepository.count() == 0) {
            authorUuidRepository.save(AuthorUuid.builder()
                    .firstName("Joe")
                    .lastName("Buck")
                    .build());
        }
    }

    private void createBookUuids() {
        if (bookUuidRepository.count() == 0) {
            bookUuidRepository.save(BookUuid.builder()
                    .title("All about Uuids")
                    .build());
        }
    }
}
