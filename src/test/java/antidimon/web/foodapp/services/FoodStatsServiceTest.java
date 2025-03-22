package antidimon.web.foodapp.services;


import antidimon.web.foodapp.mappers.FoodStatMapper;
import antidimon.web.foodapp.models.dto.stat.*;
import antidimon.web.foodapp.models.entities.Dish;
import antidimon.web.foodapp.models.entities.FoodStat;
import antidimon.web.foodapp.models.entities.MyUser;
import antidimon.web.foodapp.models.entities.StatStatus;
import antidimon.web.foodapp.repositories.FoodStatsRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FoodStatsServiceTest {

    @Mock
    private FoodStatsRepository foodStatsRepository;

    @Mock
    private DishService dishService;

    @Mock
    private MyUserService myUserService;

    @Mock
    private FoodStatMapper foodStatMapper;

    @InjectMocks
    private FoodStatsService foodStatsService;

    private MyUser user;
    private Dish dish;
    private FoodStat foodStat;
    private FoodStatInputDTO foodStatInputDTO;
    private FoodStatOutputDTO foodStatOutputDTO;
    private FoodStatOutputDTOWithoutID foodStatOutputDTOWithoutID;


    @BeforeEach
    void setUp() {
        user = new MyUser();
        user.setId(1L);

        dish = new Dish();
        dish.setId(1L);
        dish.setName("Гречка отварная");
        dish.setCalories(new BigDecimal("120.00"));
        dish.setProtein(new BigDecimal("3.20"));
        dish.setFat(new BigDecimal("4.15"));
        dish.setCarbs(new BigDecimal("19.05"));

        foodStat = new FoodStat();
        foodStat.setId(1L);
        foodStat.setUser(user);
        foodStat.setDish(dish);
        foodStat.setStatus(StatStatus.BREAKFAST);
        foodStat.setGrams(new BigDecimal("100.00"));
        foodStat.setCalories(new BigDecimal("120.00"));
        foodStat.setProtein(new BigDecimal("3.20"));
        foodStat.setFat(new BigDecimal("4.15"));
        foodStat.setCarbs(new BigDecimal("19.05"));
        foodStat.setDate(LocalDateTime.now());

        foodStatInputDTO = new FoodStatInputDTO();
        foodStatInputDTO.setUserId(1L);
        foodStatInputDTO.setDishName("Гречка отварная");
        foodStatInputDTO.setStatus(StatStatus.BREAKFAST);
        foodStatInputDTO.setGrams(new BigDecimal("100.00"));

        foodStatOutputDTO = new FoodStatOutputDTO();
        foodStatOutputDTO.setUserId(1L);
        foodStatOutputDTO.setDishName("Гречка отварная");
        foodStatOutputDTO.setStatus(StatStatus.BREAKFAST);
        foodStatOutputDTO.setGrams(new BigDecimal("100.00"));
        foodStatOutputDTO.setCalories(new BigDecimal("120.00"));
        foodStatOutputDTO.setProtein(new BigDecimal("3.20"));
        foodStatOutputDTO.setFat(new BigDecimal("4.15"));
        foodStatOutputDTO.setCarbs(new BigDecimal("19.05"));
        foodStatOutputDTO.setDate(LocalDateTime.now());

        foodStatOutputDTOWithoutID = new FoodStatOutputDTOWithoutID();
        foodStatOutputDTOWithoutID.setDishName("Гречка отварная");
        foodStatOutputDTOWithoutID.setStatus(StatStatus.BREAKFAST);
        foodStatOutputDTOWithoutID.setGrams(new BigDecimal("100.00"));
        foodStatOutputDTOWithoutID.setCalories(new BigDecimal("120.00"));
        foodStatOutputDTOWithoutID.setProtein(new BigDecimal("3.20"));
        foodStatOutputDTOWithoutID.setFat(new BigDecimal("4.15"));
        foodStatOutputDTOWithoutID.setCarbs(new BigDecimal("19.05"));
        foodStatOutputDTOWithoutID.setDate(LocalDateTime.now());
    }


    @Test
    void getStat_ShouldReturnFoodStat_WhenStatExists() {
        when(foodStatsRepository.findById(1L)).thenReturn(Optional.of(foodStat));

        FoodStat result = foodStatsService.getStat(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(foodStatsRepository, times(1)).findById(1L);
    }

    @Test
    void getStat_ShouldThrowException_WhenStatNotFound() {
        when(foodStatsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> foodStatsService.getStat(1L));
        verify(foodStatsRepository, times(1)).findById(1L);
    }


    @Test
    void getStatDTO_ShouldReturnDTO_WhenStatExists() {
        when(foodStatsRepository.findById(1L)).thenReturn(Optional.of(foodStat));
        when(foodStatMapper.toOutputDTO(foodStat)).thenReturn(foodStatOutputDTO);

        FoodStatOutputDTO result = foodStatsService.getStatDTO(1L);

        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        assertEquals("Гречка отварная", result.getDishName());
        verify(foodStatsRepository, times(1)).findById(1L);
        verify(foodStatMapper, times(1)).toOutputDTO(foodStat);
    }

    @Test
    void getAllStatsDTO_ShouldReturnListOfDTOs_WhenStatsExist() {
        when(foodStatsRepository.findAll()).thenReturn(List.of(foodStat));
        when(foodStatMapper.toOutputDTO(foodStat)).thenReturn(foodStatOutputDTO);

        List<FoodStatOutputDTO> result = foodStatsService.getAllStatsDTO();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Гречка отварная", result.get(0).getDishName());
        verify(foodStatsRepository, times(1)).findAll();
        verify(foodStatMapper, times(1)).toOutputDTO(foodStat);
    }


    @Test
    void getUserAllStatsWithoutIDDTO_ShouldReturnListWithoutId_WhenStatsExist() {
        when(foodStatsRepository.findAll()).thenReturn(List.of(foodStat));
        when(foodStatMapper.toOutputDTO(foodStat)).thenReturn(foodStatOutputDTO);
        when(foodStatMapper.toOutputDTOWithoutId(foodStatOutputDTO)).thenReturn(foodStatOutputDTOWithoutID);

        List<FoodStatOutputDTOWithoutID> result = foodStatsService.getUserAllStatsWithoutIDDTO(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Гречка отварная", result.get(0).getDishName());
        verify(foodStatsRepository, times(1)).findAll();
        verify(foodStatMapper, times(1)).toOutputDTO(foodStat);
        verify(foodStatMapper, times(1)).toOutputDTOWithoutId(foodStatOutputDTO);
    }


    @Test
    void getTodayStatsDTO_ShouldReturnTodayStats_WhenStatsExist() {
        when(foodStatsRepository.findAll()).thenReturn(List.of(foodStat));
        when(foodStatMapper.toOutputDTO(foodStat)).thenReturn(foodStatOutputDTO);

        List<FoodStatOutputDTO> result = foodStatsService.getTodayStatsDTO();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(LocalDate.now(), result.get(0).getDate().toLocalDate());
        verify(foodStatsRepository, times(1)).findAll();
        verify(foodStatMapper, times(1)).toOutputDTO(foodStat);
    }


    @Test
    void getAllTodayTotalStats_ShouldReturnTotalStats_WhenStatsExist() {
        when(foodStatsRepository.findAll()).thenReturn(List.of(foodStat));
        when(foodStatMapper.toOutputDTO(foodStat)).thenReturn(foodStatOutputDTO);
        when(myUserService.getUserCalorieNorm(1L)).thenReturn(new BigDecimal("2000.00"));

        List<DayTotalFoodStats> result = foodStatsService.getAllTodayTotalStats();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUserId());
        assertEquals(new BigDecimal("120.00"), result.get(0).getTotalCalories());
        verify(foodStatsRepository, times(1)).findAll();
        verify(foodStatMapper, times(1)).toOutputDTO(foodStat);
        verify(myUserService, times(1)).getUserCalorieNorm(1L);
    }



    @Test
    void getTodayTotalUserStat_ShouldReturnTotalStat_WhenStatsExist() {
        when(foodStatsRepository.findAll()).thenReturn(List.of(foodStat));
        when(foodStatMapper.toOutputDTO(foodStat)).thenReturn(foodStatOutputDTO);
        when(myUserService.getUserCalorieNorm(1L)).thenReturn(new BigDecimal("2000.00"));

        DayTotalFoodStats result = foodStatsService.getTodayTotalUserStat(1L);

        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        assertEquals(new BigDecimal("120.00"), result.getTotalCalories());
        verify(foodStatsRepository, times(1)).findAll();
        verify(foodStatMapper, times(1)).toOutputDTO(foodStat);
        verify(myUserService, times(1)).getUserCalorieNorm(1L);
    }



    @Test
    void getTodayTotalUserStat_ShouldThrowException_WhenStatsNotFound() {
        when(foodStatsRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NoSuchElementException.class, () -> foodStatsService.getTodayTotalUserStat(1L));
        verify(foodStatsRepository, times(1)).findAll();
    }


    @Test
    void createStat_ShouldReturnDTO_WhenInputIsValid() {
        when(dishService.getDishByName("Гречка отварная")).thenReturn(dish);
        when(myUserService.getUserById(1L)).thenReturn(user);
        when(foodStatsRepository.save(any(FoodStat.class))).thenReturn(foodStat);
        when(foodStatsRepository.findById(anyLong())).thenReturn(Optional.of(foodStat));
        when(foodStatMapper.toOutputDTO(foodStat)).thenReturn(foodStatOutputDTO);

        FoodStatOutputDTO result = foodStatsService.createStat(foodStatInputDTO);

        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        assertEquals("Гречка отварная", result.getDishName());
        verify(dishService, times(1)).getDishByName("Гречка отварная");
        verify(myUserService, times(1)).getUserById(1L);
        verify(foodStatsRepository, times(1)).save(any(FoodStat.class));
        verify(foodStatMapper, times(1)).toOutputDTO(foodStat);
    }


    @Test
    void createStat_ShouldThrowException_WhenInputIsInvalid() {
        foodStatInputDTO.setDishName(null);

        assertThrows(IllegalArgumentException.class, () -> foodStatsService.createStat(foodStatInputDTO));
        verify(foodStatsRepository, never()).save(any(FoodStat.class));
    }

    @Test
    void createStats_ShouldReturnListOfDTOs_WhenInputIsValid() {
        when(dishService.getDishByName("Гречка отварная")).thenReturn(dish);
        when(myUserService.getUserById(1L)).thenReturn(user);
        when(foodStatsRepository.save(any(FoodStat.class))).thenReturn(foodStat);
        when(foodStatsRepository.findById(anyLong())).thenReturn(Optional.of(foodStat));
        when(foodStatMapper.toOutputDTO(foodStat)).thenReturn(foodStatOutputDTO);

        List<FoodStatOutputDTO> result = foodStatsService.createStats(List.of(foodStatInputDTO));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUserId());
        verify(dishService, times(1)).getDishByName("Гречка отварная");
        verify(myUserService, times(1)).getUserById(1L);
        verify(foodStatsRepository, times(1)).save(any(FoodStat.class));
        verify(foodStatMapper, times(1)).toOutputDTO(foodStat);
    }


    @Test
    void deleteStat_ShouldDeleteStat_WhenStatExists() {
        when(foodStatsRepository.findById(1L)).thenReturn(Optional.of(foodStat));
        doNothing().when(foodStatsRepository).delete(foodStat);

        foodStatsService.deleteStat(1L);

        verify(foodStatsRepository, times(1)).findById(1L);
        verify(foodStatsRepository, times(1)).delete(foodStat);
    }

    @Test
    void deleteStat_ShouldThrowException_WhenStatNotFound() {
        when(foodStatsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> foodStatsService.deleteStat(1L));
        verify(foodStatsRepository, times(1)).findById(1L);
        verify(foodStatsRepository, never()).delete(any(FoodStat.class));
    }

    @Test
    void getUserHistoryTotalStats_ShouldReturnEmptyList_WhenNoStatsExist() {
        when(foodStatsRepository.findAll()).thenReturn(Collections.emptyList());

        List<HistoryDayTotalFoodStats> result = foodStatsService.getUserHistoryTotalStats(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(foodStatsRepository, times(1)).findAll();
    }






}
