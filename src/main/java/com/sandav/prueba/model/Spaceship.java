package com.sandav.prueba.model;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Cacheable
public class Spaceship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private boolean isFilm;
    private String filmName;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Boolean getIsFilm() {
        return isFilm;
    }
    public void setIsFilm(Boolean isFilm) {
        this.isFilm = isFilm;
    }
    public String getFilmName() {
        return filmName;
    }
    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

}