package com.my.library.service;

import com.my.library.dto.AuthorDto;
import com.my.library.exception.AuthorNotFoundException;
import com.my.library.model.Author;
import com.my.library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

  private final AuthorRepository authorRepository;

  public List<Author> getAllAuthors() {
    return authorRepository.findAll();
  }

  @Transactional
  public Author createAuthor(AuthorDto authorDto) {
    Author author = new Author();
    author.setName(authorDto.getName());
    author.setBio(authorDto.getBio());
    return authorRepository.save(author);
  }

  public Optional<Author> getAuthorById(Long id) {
    return authorRepository.findById(id);
  }

  @Transactional
  public Author updateAuthorById(Long id, AuthorDto authorDto) {
    Author author = authorRepository.findById(id)
        .orElseThrow(() -> new AuthorNotFoundException(id));
    author.setName(authorDto.getName());
    author.setBio(authorDto.getBio());
    return authorRepository.save(author);
  }

  @Transactional
  public void deleteAuthor(Long id) {
    authorRepository.findById(id)
        .orElseThrow(() -> new AuthorNotFoundException(id));
    authorRepository.deleteById(id);
  }

}