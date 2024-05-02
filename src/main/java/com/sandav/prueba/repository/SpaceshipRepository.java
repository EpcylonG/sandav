package com.sandav.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sandav.prueba.model.Spaceship;

@Repository
public interface SpaceshipRepository extends JpaRepository<Spaceship, Long>{

    Spaceship findByName(String name);
    
}
