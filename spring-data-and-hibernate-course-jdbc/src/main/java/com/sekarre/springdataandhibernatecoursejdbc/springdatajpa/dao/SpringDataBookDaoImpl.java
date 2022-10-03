package com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.dao;

import com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.domain.BookSpringData;
import com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SpringDataBookDaoImpl implements SpringDataBookDao {

    private final BookRepository bookRepository;

    @Override
    public List<BookSpringData> findAllSortByTitle(Pageable pageable) {
        return bookRepository.findAll(pageable).getContent();
    }

    @Override
    public List<BookSpringData> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).getContent();
    }

    @Override
    public BookSpringData getById(Long id) {
        return bookRepository.getReferenceById(id);
    }

    @Override
    public BookSpringData findByTitle(String title) {
        return bookRepository.findByTitle(title).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public BookSpringData saveNewBook(BookSpringData book) {
        return bookRepository.save(book);
    }

    @Override
    public BookSpringData updateBook(BookSpringData book) {
        BookSpringData foundBook = bookRepository.getReferenceById(book.getId());
        foundBook.setPublisher(book.getPublisher());
        foundBook.setTitle(book.getTitle());
        foundBook.setIsbn(book.getIsbn());
        foundBook.setAuthorId(book.getAuthorId());
        return bookRepository.save(foundBook);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
