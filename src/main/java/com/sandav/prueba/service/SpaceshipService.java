package com.sandav.prueba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandav.prueba.model.Spaceship;
import com.sandav.prueba.repository.SpaceshipRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SpaceshipService {

    private SpaceshipRepository spaceshipRepository;

    @Autowired
    public SpaceshipService(SpaceshipRepository spaceshipRepository) {
        this.spaceshipRepository = spaceshipRepository;
    }
    
    public Spaceship findById(String idValue){
        try{
            Long id = checkId(idValue);
            return spaceshipRepository.findById(id).orElseThrow();
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException();
        }
    }

    public boolean create(Spaceship spaceship){
        if ((spaceship.getName() == null || spaceship.getIsFilm() == null || spaceship.getFilmName() == null) ||
            (spaceship.getId() <= 0 || spaceship.getName().isEmpty() || spaceship.getFilmName().isEmpty())) {
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

        return Long.parseLong(id);
    }
}
