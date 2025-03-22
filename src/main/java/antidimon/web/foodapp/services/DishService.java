package antidimon.web.foodapp.services;


import antidimon.web.foodapp.mappers.DishMapper;
import antidimon.web.foodapp.models.dto.dish.DishEditDTO;
import antidimon.web.foodapp.models.dto.dish.DishInputDTO;
import antidimon.web.foodapp.models.dto.dish.DishOutputDTO;
import antidimon.web.foodapp.models.entities.Dish;
import antidimon.web.foodapp.repositories.DishRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Validated
@Slf4j
public class DishService {

    private DishRepository dishRepository;
    private DishMapper dishMapper;

    protected Dish getDishById(long id) throws NoSuchElementException {
        Optional<Dish> dish = dishRepository.findById(id);
        if (dish.isPresent()) return dish.get();
        throw new NoSuchElementException("Dish not found");
    }

    public Dish getDishByName(String dishName) throws NoSuchElementException {
        Optional<Dish> dish = dishRepository.findByName(dishName);
        if (dish.isPresent()) return dish.get();
        throw new NoSuchElementException("Dish not found");
    }

    public DishOutputDTO getDishDTO(long id) throws NoSuchElementException {
        return dishMapper.toOutputDTO(getDishById(id));
    }

    protected List<Dish> getDishes(){
        return dishRepository.findAll();
    }

    public List<DishOutputDTO> getDishesDTO() {
        var dishes = getDishes();
        return dishes.stream().map(dishMapper::toOutputDTO).toList();
    }

    @Transactional
    public DishOutputDTO createDish(@Valid DishInputDTO dishInputDTO)
            throws ConstraintViolationException, IllegalArgumentException {

        this.checkCaloriesByPFC(dishInputDTO.getCalories(), dishInputDTO.getProtein(),
                dishInputDTO.getFat(), dishInputDTO.getCarbs());

        Dish dish = dishMapper.toEntity(dishInputDTO);
        dishRepository.save(dish);
        return dishMapper.toOutputDTO(dish);
    }


    @Transactional
    public void deleteDish(long id) throws NoSuchElementException {

        Dish dish = getDishById(id);
        dishRepository.delete(dish);
    }



    @Transactional
    public DishOutputDTO editDish(long id, DishEditDTO dishEditDTO) throws NoSuchElementException {

        Dish dish = getDishById(id);
        if (dishEditDTO.getName() != null && !dish.getName().isBlank()) dish.setName(dishEditDTO.getName());

        if (dishEditDTO.getCalories() != null) {
            this.checkNewCalories(dishEditDTO);
            dish.setCalories(dishEditDTO.getCalories());
        }

        this.checkNewPFC(dishEditDTO, dish);
        if (dishEditDTO.getProtein() != null) dish.setProtein(dishEditDTO.getProtein());
        if (dishEditDTO.getFat() != null) dish.setFat(dishEditDTO.getFat());
        if (dishEditDTO.getCarbs() != null) dish.setCarbs(dishEditDTO.getCarbs());

        dishRepository.save(dish);
        return dishMapper.toOutputDTO(dish);
    }

    private void checkCaloriesByPFC(BigDecimal inputCalories, BigDecimal protein, BigDecimal fat, BigDecimal carbs)
            throws IllegalArgumentException {

        BigDecimal calories = protein.multiply(UserNormService.caloriesInProtein)
                .add(fat.multiply(UserNormService.caloriesInFat))
                .add(carbs.multiply(UserNormService.caloriesInCarb));

        BigDecimal diff = inputCalories.subtract(calories);
        if (diff.compareTo(BigDecimal.ZERO) < 0 || diff.compareTo(BigDecimal.ONE) > 0)
            throw new IllegalArgumentException("The amount of calories does not match the amount of PFC");

    }

    private void checkNewCalories(DishEditDTO dishEditDTO) throws IllegalArgumentException {

        if (dishEditDTO.getCalories().compareTo(BigDecimal.ZERO) < 0 ||
                dishEditDTO.getCalories().compareTo(BigDecimal.valueOf(862)) > 0) {
            throw new IllegalArgumentException("Calories must be between 0 and 862");
        }
    }

    private void checkNewPFC(DishEditDTO dishEditDTO, Dish dish) throws IllegalArgumentException {

        BigDecimal newProtein = dishEditDTO.getProtein() == null ? dish.getProtein() : dishEditDTO.getProtein();
        BigDecimal newFat = dishEditDTO.getFat() == null ? dish.getFat() : dishEditDTO.getFat();
        BigDecimal newCarbs = dishEditDTO.getCarbs() == null ? dish.getCarbs() : dishEditDTO.getCarbs();

        if (newProtein.add(newFat).add(newCarbs).compareTo(BigDecimal.valueOf(100)) > 0)
            throw new IllegalArgumentException("Protein+fat+carbs <= 100");

        this.checkCaloriesByPFC(dish.getCalories(), newProtein, newFat, newCarbs);

    }
}
