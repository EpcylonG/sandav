package com.sandav.prueba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sandav.prueba.model.Spaceship;
import com.sandav.prueba.repository.SpaceshipRepository;
import com.sandav.prueba.utils.LoggerController;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/spaceships")
public class SpaceshipController {
    
    @Autowired
    private SpaceshipRepository spaceshipRepository;

    private LoggerController logger = new LoggerController();

    @GetMapping("/")
     public Page<Spaceship> getAll(@RequestParam(required = false) Integer page,
                                   @RequestParam(required = false) Integer size) {
        if(page != null && size != null){
            PageRequest pageable = PageRequest.of(page, size);
            return spaceshipRepository.findAll(pageable);
        }
        return new PageImpl<>(spaceshipRepository.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Spaceship> getById(@PathVariable String id) {
        if (!id.matches("\\d+")) {
            throw new IllegalArgumentException();
        }

        Long idValue = Long.parseLong(id);

        if (idValue <= 0){
            logger.info("El id introducido es negativo.");
        }

        if (!spaceshipRepository.existsById(idValue)) {
            throw new EntityNotFoundException();
        }

        Spaceship spaceship = spaceshipRepository.findById(idValue).orElseThrow();
        return ResponseEntity.ok(spaceship);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<Spaceship>> getByName(@PathVariable String name) {
        List<Spaceship> spaceships = spaceshipRepository.findAllByNameContainingIgnoreCase(name.toLowerCase());

        if (spaceships.isEmpty()) {
            return ResponseEntity.notFound().build();
        } 

        return ResponseEntity.ok(spaceships);
    }

    @PostMapping("/")
    public ResponseEntity<Spaceship> create(@RequestBody Spaceship spaceship) {
        if (spaceship.getName() == null || spaceship.getIsFilm() == null || spaceship.getFilmName() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (spaceship.getName().isEmpty() || spaceship.getFilmName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(spaceshipRepository.save(spaceship));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        try {

            Long spaceshipId = Long.parseLong(id);

            if (spaceshipRepository.existsById(spaceshipId)) {
                spaceshipRepository.deleteById(spaceshipId);
                return ResponseEntity.ok("Spaceship with ID: " + id + " was deleted");
            }
            return ResponseEntity.notFound().build();

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("ID must be a valid number");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Spaceship> update(@PathVariable Long id, @RequestBody Spaceship newSpaceship) {
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

        Spaceship spaceshipActualizado = spaceshipRepository.save(spaceship);
        return ResponseEntity.ok(spaceshipActualizado);
    }
}
