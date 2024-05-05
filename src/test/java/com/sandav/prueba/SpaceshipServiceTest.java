package com.sandav.prueba;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sandav.prueba.model.Spaceship;
import com.sandav.prueba.repository.SpaceshipRepository;
import com.sandav.prueba.service.SpaceshipService;

@ExtendWith(MockitoExtension.class)
class SpaceshipServiceTest {

    @Mock
    private SpaceshipRepository spaceshipRepository;

    @InjectMocks
    private SpaceshipService spaceshipService;

    @Test
    void testFindById() {
        Long id = 1L;
        Spaceship spaceship = new Spaceship();
        spaceship.setId(id);
        when(spaceshipRepository.findById(id)).thenReturn(Optional.of(spaceship));
		
        Spaceship result = spaceshipService.findById("1");

        assertEquals(spaceship, result);
    }

	@Test
	void testCreateSuccess() {
		Spaceship spaceship = new Spaceship();
		spaceship.setId(1L);
		spaceship.setName("X-Wing");
		spaceship.setIsFilm(true);
		spaceship.setFilmName("Star Wars");

		when(spaceshipRepository.save(spaceship)).thenReturn(spaceship);

		boolean result = spaceshipService.create(spaceship);

		assertTrue(result);
	}

	@Test
	void testCreateFailed() {
		Spaceship spaceship = new Spaceship();

		boolean result = spaceshipService.create(spaceship);

		assertFalse(result);
	}

	@Test
	void testDeleteSuccess() {
		Long id = 1L;
		when(spaceshipRepository.existsById(id)).thenReturn(true);

		spaceshipService.delete(String.valueOf(id));

		verify(spaceshipRepository).deleteById(id);
	}

	@Test
	void testDeleteFailed() {
		Long id = 1L;
		when(spaceshipRepository.existsById(id)).thenReturn(false);

		spaceshipService.delete(String.valueOf(id));

		verify(spaceshipRepository, never()).deleteById(anyLong());
	}

	@Test
	void testUpdateSuccess() {
		Long id = 1L;
		Spaceship existingSpaceship = new Spaceship();
		existingSpaceship.setId(id);
		existingSpaceship.setName("X-Wing");
		existingSpaceship.setIsFilm(true);
		existingSpaceship.setFilmName("Star Wars");

		Spaceship newSpaceship = new Spaceship();
		newSpaceship.setName("TIE Fighter");

		when(spaceshipRepository.findById(id)).thenReturn(Optional.of(existingSpaceship));
		when(spaceshipRepository.save(existingSpaceship)).thenReturn(existingSpaceship);

		Spaceship updatedSpaceship = spaceshipService.update(String.valueOf(id), newSpaceship);

		assertEquals(newSpaceship.getName(), updatedSpaceship.getName());
		assertEquals(existingSpaceship.getIsFilm(), updatedSpaceship.getIsFilm());
		assertEquals(existingSpaceship.getFilmName(), updatedSpaceship.getFilmName());
	}
}