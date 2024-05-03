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
public class SpaceshipServiceTest {

    @Mock
    private SpaceshipRepository spaceshipRepository;

    @InjectMocks
    private SpaceshipService spaceshipService;

    @Test
    public void testFindById() {
        Long id = 1L;
        Spaceship spaceship = new Spaceship();
        spaceship.setId(id);
        when(spaceshipRepository.findById(id)).thenReturn(Optional.of(spaceship));
		
        Spaceship result = spaceshipService.findById(String.valueOf(id));

        assertEquals(spaceship, result);
    }

	@Test
	public void testCreateSuccess() {
		Spaceship spaceship = new Spaceship();
		spaceship.setName("X-Wing");
		spaceship.setIsFilm(true);
		spaceship.setFilmName("Star Wars");

		when(spaceshipRepository.save(spaceship)).thenReturn(spaceship);

		boolean result = spaceshipService.create(spaceship);

		assertTrue(result);
	}

	@Test
	public void testCreateFailed() {
		Spaceship spaceship = new Spaceship();

		boolean result = spaceshipService.create(spaceship);

		assertFalse(result);
	}

	@Test
	public void testDeleteSuccess() {
		Long id = 1L;
		when(spaceshipRepository.existsById(id)).thenReturn(true);

		spaceshipService.delete(String.valueOf(id));

		verify(spaceshipRepository).deleteById(id);
	}

	@Test
	public void testDeleteFailed() {
		Long id = 1L;
		when(spaceshipRepository.existsById(id)).thenReturn(false);

		spaceshipService.delete(String.valueOf(id));

		verify(spaceshipRepository, never()).deleteById(anyLong());
	}

	@Test
	public void testUpdateSuccess() {
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