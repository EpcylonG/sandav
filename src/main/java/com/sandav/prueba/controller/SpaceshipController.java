package com.sandav.prueba.controller;

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

@RestController
@RequestMapping("/spaceships")
public class SpaceshipController {
    
    @Autowired
    private SpaceshipRepository spaceshipRepository;

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
    public ResponseEntity<Spaceship> getById(@PathVariable Long id) {
        //TODO calcular que sea un numero y no una letra
        Spaceship spaceship = spaceshipRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(spaceship);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Spaceship> getByName(@PathVariable String name) {
        //TODO pasar a minuscula y que sea solo una parte del nombre
        Spaceship spaceship = spaceshipRepository.findByName(name);
        return ResponseEntity.ok(spaceship);
    }

    @PostMapping("/")
    public ResponseEntity<Spaceship> create(@RequestBody Spaceship spaceship) {
        //TODO ver que vengan todos los campos
        return ResponseEntity.ok(spaceshipRepository.save(spaceship));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        //TODO ver que el que quieres eliminar existe
        Spaceship spaceship = spaceshipRepository.findById(id).orElseThrow();
        spaceshipRepository.delete(spaceship);
        return ResponseEntity.ok("Spaceship with ID: " + id + " was deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Spaceship> update(@PathVariable Long id, @RequestBody Spaceship newSpaceship) {
        Spaceship spaceship = spaceshipRepository.findById(id).orElseThrow();

        //TODO si no viene un valor, coge el que tiene por defecto
        spaceship.setName(newSpaceship.getName());
        spaceship.setIsFilm(newSpaceship.getIsFilm());
        spaceship.setFilmName(newSpaceship.getFilmName());

        Spaceship productoActualizado = spaceshipRepository.save(spaceship);
        return ResponseEntity.ok(productoActualizado);
    }
}
