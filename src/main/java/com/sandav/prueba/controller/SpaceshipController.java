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
import com.sandav.prueba.service.SpaceshipService;

@RestController
@RequestMapping("/spaceships")
public class SpaceshipController {
    
    private SpaceshipRepository spaceshipRepository;

    private SpaceshipService spaceshipService;

    @Autowired
    public SpaceshipController(SpaceshipRepository spaceshipRepository, SpaceshipService spaceshipService) {
        this.spaceshipRepository = spaceshipRepository;
        this.spaceshipService = spaceshipService;
    }

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
        return ResponseEntity.ok(spaceshipService.findById(id));
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
        if(spaceshipService.create(spaceship)){
            return ResponseEntity.ok(spaceship);
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        this.spaceshipService.delete(id);
        if(spaceshipService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body("Eliminado correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Spaceship> update(@PathVariable String id, @RequestBody Spaceship newSpaceship) {
        return ResponseEntity.ok(spaceshipService.update(id, newSpaceship));
    }
}

/*
 * Utilizar cachés de algún tipo.
 * 
 * Docker
 * Swagger
 */