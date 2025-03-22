package antidimon.web.foodapp.services;


import antidimon.web.foodapp.mappers.DishMapper;
import antidimon.web.foodapp.models.dto.dish.DishEditDTO;
import antidimon.web.foodapp.models.dto.dish.DishInputDTO;
import antidimon.web.foodapp.models.dto.dish.DishOutputDTO;
import antidimon.web.foodapp.models.entities.Dish;
import antidimon.web.foodapp.repositories.DishRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DishServiceTest {


    @Mock
    private DishRepository dishRepository;

    @Mock
    private DishMapper dishMapper;

    @InjectMocks
    private DishService dishService;

    private Dish dish;
    private DishInputDTO dishInputDTO;
    private DishOutputDTO dishOutputDTO;
    private DishEditDTO dishEditDTO;


    @BeforeEach
    void setUp() {
        dish = new Dish();
        dish.setId(1L);
        dish.setName("Гречка отварная");
        dish.setCalories(new BigDecimal("127.00"));
        dish.setProtein(new BigDecimal("3.20"));
        dish.setFat(new BigDecimal("4.15"));
        dish.setCarbs(new BigDecimal("19.05"));

        dishInputDTO = new DishInputDTO();
        dishInputDTO.setName("Гречка отварная");
        dishInputDTO.setCalories(new BigDecimal("127.00"));
        dishInputDTO.setProtein(new BigDecimal("3.20"));
        dishInputDTO.setFat(new BigDecimal("4.15"));
        dishInputDTO.setCarbs(new BigDecimal("19.05"));

        dishOutputDTO = new DishOutputDTO();
        dishOutputDTO.setId(1L);
        dishOutputDTO.setName("Гречка отварная");
        dishOutputDTO.setCalories(new BigDecimal("127.00"));
        dishOutputDTO.setProtein(new BigDecimal("3.20"));
        dishOutputDTO.setFat(new BigDecimal("4.15"));
        dishOutputDTO.setCarbs(new BigDecimal("19.05"));

        dishEditDTO = new DishEditDTO();
        dishEditDTO.setName("Обновленная гречка");
        dishEditDTO.setCalories(new BigDecimal("142.00"));
        dishEditDTO.setProtein(new BigDecimal("4.00"));
        dishEditDTO.setFat(new BigDecimal("5.00"));
        dishEditDTO.setCarbs(new BigDecimal("20.00"));
    }


    @Test
    void getDishById_ShouldReturnDish_WhenDishExists() {
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));

        Dish result = dishService.getDishById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Гречка отварная", result.getName());
        verify(dishRepository, times(1)).findById(1L);
    }

    @Test
    void getDishById_ShouldThrowException_WhenDishNotFound() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> dishService.getDishById(1L));
        verify(dishRepository, times(1)).findById(1L);
    }


    @Test
    void getDishDTO_ShouldReturnDTO_WhenDishExists() {
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));
        when(dishMapper.toOutputDTO(dish)).thenReturn(dishOutputDTO);

        DishOutputDTO result = dishService.getDishDTO(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Гречка отварная", result.getName());
        verify(dishRepository, times(1)).findById(1L);
        verify(dishMapper, times(1)).toOutputDTO(dish);
    }

    @Test
    void getDishDTO_ShouldThrowException_WhenDishNotFound() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> dishService.getDishDTO(1L));
        verify(dishRepository, times(1)).findById(1L);
    }


    @Test
    void getDishesDTO_ShouldReturnListOfDTOs_WhenDishesExist() {
        when(dishRepository.findAll()).thenReturn(List.of(dish));
        when(dishMapper.toOutputDTO(dish)).thenReturn(dishOutputDTO);

        List<DishOutputDTO> result = dishService.getDishesDTO();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Гречка отварная", result.get(0).getName());
        verify(dishRepository, times(1)).findAll();
        verify(dishMapper, times(1)).toOutputDTO(dish);
    }

    @Test
    void getDishesDTO_ShouldReturnEmptyList_WhenNoDishesExist() {
        when(dishRepository.findAll()).thenReturn(Collections.emptyList());

        List<DishOutputDTO> result = dishService.getDishesDTO();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(dishRepository, times(1)).findAll();
    }


    @Test
    void createDish_ShouldReturnDTO_WhenInputIsValid() {
        when(dishMapper.toEntity(dishInputDTO)).thenReturn(dish);
        when(dishRepository.save(dish)).thenReturn(dish);
        when(dishMapper.toOutputDTO(dish)).thenReturn(dishOutputDTO);

        DishOutputDTO result = dishService.createDish(dishInputDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Гречка отварная", result.getName());
        verify(dishMapper, times(1)).toEntity(dishInputDTO);
        verify(dishRepository, times(1)).save(dish);
        verify(dishMapper, times(1)).toOutputDTO(dish);
    }

    @Test
    void createDish_ShouldThrowException_WhenInputIsInvalid() {
        when(dishMapper.toEntity(dishInputDTO)).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class, () -> dishService.createDish(dishInputDTO));
        verify(dishMapper, times(1)).toEntity(dishInputDTO);
    }

    @Test
    void createDish_ShouldThrowException_WhenCaloriesDoNotMatchPFC() {
        DishInputDTO invalidInput = new DishInputDTO();
        invalidInput.setName("Invalid Dish");
        invalidInput.setCalories(new BigDecimal("500.00"));
        invalidInput.setProtein(new BigDecimal("10.00"));
        invalidInput.setFat(new BigDecimal("10.00"));
        invalidInput.setCarbs(new BigDecimal("10.00"));

        assertThrows(IllegalArgumentException.class, () -> dishService.createDish(invalidInput));
    }



    @Test
    void deleteDish_ShouldDeleteDish_WhenDishExists() {
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));

        dishService.deleteDish(1L);

        verify(dishRepository, times(1)).findById(1L);
        verify(dishRepository, times(1)).delete(dish);
    }

    @Test
    void deleteDish_ShouldThrowException_WhenDishNotFound() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> dishService.deleteDish(1L));
        verify(dishRepository, times(1)).findById(1L);
    }




    @Test
    void editDish_ShouldReturnDTO_WhenInputIsValid() {
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));
        when(dishRepository.save(dish)).thenReturn(dish);
        when(dishMapper.toOutputDTO(dish)).thenReturn(dishOutputDTO);

        DishOutputDTO result = dishService.editDish(1L, dishEditDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Гречка отварная", result.getName());
        assertEquals(new BigDecimal("127.00"), result.getCalories());
        verify(dishRepository, times(1)).findById(1L);
        verify(dishRepository, times(1)).save(dish);
        verify(dishMapper, times(1)).toOutputDTO(dish);
    }

    @Test
    void editDish_ShouldThrowException_WhenDishNotFound() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> dishService.editDish(1L, dishEditDTO));
        verify(dishRepository, times(1)).findById(1L);
    }

    @Test
    void editDish_ShouldThrowException_WhenCaloriesDoNotMatchPFC() {
        DishEditDTO invalidEdit = new DishEditDTO();
        invalidEdit.setCalories(new BigDecimal("500.00"));
        invalidEdit.setProtein(new BigDecimal("10.00"));
        invalidEdit.setFat(new BigDecimal("10.00"));
        invalidEdit.setCarbs(new BigDecimal("10.00"));

        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));

        assertThrows(IllegalArgumentException.class, () -> dishService.editDish(1L, invalidEdit));
    }




    @Test
    void checkCaloriesByPFC_ShouldNotThrowException_WhenCaloriesMatch() {
        assertDoesNotThrow(() -> dishService.checkCaloriesByPFC(
                new BigDecimal("127.00"),
                new BigDecimal("3.20"),
                new BigDecimal("4.15"),
                new BigDecimal("19.05")
        ));
    }

    @Test
    void checkCaloriesByPFC_ShouldThrowException_WhenCaloriesDoNotMatch() {
        assertThrows(IllegalArgumentException.class, () -> dishService.checkCaloriesByPFC(
                new BigDecimal("150.00"),
                new BigDecimal("3.20"),
                new BigDecimal("4.15"),
                new BigDecimal("19.05")
        ));
    }
}
