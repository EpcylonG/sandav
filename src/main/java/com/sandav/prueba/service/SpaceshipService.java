package com.sandav.prueba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandav.prueba.model.Spaceship;
import com.sandav.prueba.repository.SpaceshipRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SpaceshipService {

    @Autowired
    SpaceshipRepository spaceshipRepository;
    
    public Spaceship findById(String idValue){
        Long id = checkId(idValue);
        if (!spaceshipRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        Spaceship spaceship = spaceshipRepository.findById(id).orElseThrow();

        return spaceship;
    }

    public boolean create(Spaceship spaceship){
        if ((spaceship.getName() == null || spaceship.getIsFilm() == null || spaceship.getFilmName() == null) ||
            (spaceship.getName().isEmpty() || spaceship.getFilmName().isEmpty())) {
            return false;
        }

        spaceshipRepository.save(spaceship);

        return true;
    }

    public void delete(String idValue){
        Long id = checkId(idValue);
        if (spaceshipRepository.existsById(id)) {
            spaceshipRepository.deleteById(id);
        }
    }

    public Spaceship update(String idValue, Spaceship newSpaceship){
        Long id = checkId(idValue);
        Spaceship spaceship = spaceshipRepository.findById(id).orElseThrow();
        
        if (newSpaceship.getName() != null) {
            spaceship.setName(newSpaceship.getName());
        }
        if (newSpaceship.getIsFilm() != null) {
            spaceship.setIsFilm(newSpaceship.getIsFilm());
        }
        if (newSpaceship.getFilmName() != null) {
            spaceship.setFilmName(newSpaceship.getFilmName());
        }

        return spaceshipRepository.save(spaceship);
    }

    private Long checkId(String id){
        if (!id.matches("\\d+")) {
            throw new NumberFormatException();
        }
        Long idValue = Long.parseLong(id);

        return idValue;
    }
}
