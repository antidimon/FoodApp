package antidimon.web.foodapp.controllers;


import antidimon.web.foodapp.models.dto.stat.DayTotalFoodStats;
import antidimon.web.foodapp.models.dto.stat.FoodStatInputDTO;
import antidimon.web.foodapp.models.dto.stat.FoodStatOutputDTO;
import antidimon.web.foodapp.models.dto.stat.HistoryDayTotalFoodStats;
import antidimon.web.foodapp.services.FoodStatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Контроллер статистики", description = "Работа с0 сбором/выдачей статистики")
@RestController
@RequestMapping("/api/stats")
@AllArgsConstructor
@Slf4j
public class FoodStatController {

    private FoodStatsService foodStatsService;

    @Operation
    @GetMapping
    public ResponseEntity<List<?>> getStats(@RequestParam(name = "userId", required = false) Long userId) {
        List<?> stats;
        if (userId != null) stats = foodStatsService.getUserAllStatsWithoutIDDTO(userId);
        else stats = foodStatsService.getAllStatsDTO();
        return ResponseEntity.ok(stats);
    }

    @Operation
    @GetMapping("/{statId}")
    public ResponseEntity<FoodStatOutputDTO> getStat(@PathVariable(name = "statId") int statId) {
        var stat = foodStatsService.getStatDTO(statId);
        return ResponseEntity.ok(stat);
    }

    @Operation
    @PostMapping
    public ResponseEntity<List<FoodStatOutputDTO>> createStat(@RequestBody List<FoodStatInputDTO> foodStatInputDTOs) {
        var stats = foodStatsService.createStats(foodStatInputDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).body(stats);
    }

    @Operation
    @DeleteMapping("/{statId}")
    public ResponseEntity<FoodStatOutputDTO> deleteStat(@PathVariable(name = "statId") int statId) {
        foodStatsService.deleteStat(statId);
        return ResponseEntity.ok().build();
    }


    @Operation
    @GetMapping("/today")
    public ResponseEntity<List<DayTotalFoodStats>> getTodayStats() {
        List<DayTotalFoodStats> stats = foodStatsService.getAllTodayTotalStats();
        return ResponseEntity.ok(stats);
    }

    @Operation
    @GetMapping("/today/{userId}")
    public ResponseEntity<DayTotalFoodStats> getUserTodayStat(@PathVariable(name = "userId") long userId){
        var stat = foodStatsService.getTodayTotalUserStat(userId);
        return ResponseEntity.ok(stat);
    }

    @Operation
    @GetMapping("/days/{userId}")
    public ResponseEntity<List<HistoryDayTotalFoodStats>> getUserDaysStats(@PathVariable(name = "userId") long userId){
        var stats = foodStatsService.getUserHistoryTotalStats(userId);
        return ResponseEntity.ok(stats);
    }

}
