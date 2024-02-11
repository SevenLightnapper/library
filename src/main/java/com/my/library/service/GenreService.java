package com.my.library.service;

import com.my.library.dto.GenreDto;
import com.my.library.exception.GenreNotFoundException;
import com.my.library.model.Genre;
import com.my.library.repository.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

  private final GenreRepository genreRepository;

  public GenreService(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
  }

  public List<Genre> getAllGenres() {
    return genreRepository.findAll();
  }

  @Transactional
  public Genre createGenre(GenreDto genreDto) {
    Genre genre = new Genre();
    genre.setName(genreDto.getName());
    return genreRepository.save(genre);
  }

  public Optional<Genre> getGenreById(Long id) {
    return genreRepository.findById(id);
  }

  @Transactional
  public Genre updateGenreById(Long id, GenreDto genreDto) {
    Genre genre = genreRepository.findById(id)
        .orElseThrow(() -> new GenreNotFoundException(id));
    genre.setName(genreDto.getName());
    return genreRepository.save(genre);
  }

  @Transactional
  public void deleteGenre(Long id) {
    genreRepository.findById(id)
        .orElseThrow(() -> new GenreNotFoundException(id));
    genreRepository.deleteById(id);
  }

}