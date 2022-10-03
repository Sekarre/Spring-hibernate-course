package com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.dao;

import com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.domain.AuthorSpringData;
import com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SpringDataAuthorDaoImpl implements SpringDataAuthorDao {

    private final AuthorRepository authorRepository;

    @Override
    public List<AuthorSpringData> findAllByLastName(String lastName, Pageable pageable) {
        return authorRepository.findAllByLastName(lastName, pageable).getContent();
    }

    @Override
    public AuthorSpringData getById(Long id) {
        return authorRepository.getReferenceById(id);
    }

    @Override
    public AuthorSpringData findByName(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public AuthorSpringData saveNewAuthor(AuthorSpringData author) {
        return authorRepository.save(author);
    }

    @Override
    public AuthorSpringData updateAuthor(AuthorSpringData author) {
        AuthorSpringData foundAuthor = authorRepository.getReferenceById(author.getId());
        foundAuthor.setFirstName(author.getFirstName());
        foundAuthor.setLastName(author.getLastName());
        return authorRepository.save(foundAuthor);
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}
