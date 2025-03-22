package antidimon.web.foodapp.controllers;


import antidimon.web.foodapp.models.dto.dish.DishEditDTO;
import antidimon.web.foodapp.models.dto.dish.DishInputDTO;
import antidimon.web.foodapp.models.dto.dish.DishOutputDTO;
import antidimon.web.foodapp.services.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Контроллер блюд", description = "Работа с блюдами")
@RestController
@RequestMapping("/api/dishes")
@AllArgsConstructor
public class DishController {

    private DishService dishService;

    @Operation
    @GetMapping
    public ResponseEntity<List<DishOutputDTO>> getDishes(){
        var dishes = dishService.getDishesDTO();
        return ResponseEntity.ok(dishes);
    }

    @Operation
    @GetMapping("/{dishId}")
    public ResponseEntity<DishOutputDTO> getDishById(@PathVariable(name = "dishId") long dishId){
        var dish = dishService.getDishDTO(dishId);
        return ResponseEntity.ok(dish);
    }

    @Operation
    @PostMapping
    public ResponseEntity<DishOutputDTO> createDish(@RequestBody DishInputDTO dishInputDTO){
        var dish = dishService.createDish(dishInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dish);
    }

    @Operation
    @PatchMapping("/{dishId}")
    public ResponseEntity<DishOutputDTO> editDish(@RequestBody DishEditDTO dishEditDTO,
                                                  @PathVariable(name = "dishId") long dishId){
        var dish = dishService.editDish(dishId, dishEditDTO);
        return ResponseEntity.ok(dish);
    }

    @Operation
    @DeleteMapping("/{dishId}")
    public ResponseEntity<?> deleteDish(@PathVariable(name = "dishId") long dishId){
        dishService.deleteDish(dishId);
        return ResponseEntity.ok().build();
    }


}
