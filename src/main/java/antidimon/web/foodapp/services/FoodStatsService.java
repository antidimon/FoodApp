package antidimon.web.foodapp.services;

import antidimon.web.foodapp.mappers.FoodStatMapper;
import antidimon.web.foodapp.models.dto.stat.*;
import antidimon.web.foodapp.models.entities.Dish;
import antidimon.web.foodapp.models.entities.FoodStat;
import antidimon.web.foodapp.models.entities.MyUser;
import antidimon.web.foodapp.repositories.FoodStatsRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Validated
@Slf4j
public class FoodStatsService {

    private FoodStatsRepository foodStatsRepository;
    private DishService dishService;
    private MyUserService myUserService;
    private FoodStatMapper foodStatMapper;



    protected FoodStat getStat(long id) throws NoSuchElementException {
        Optional<FoodStat> foodStat = foodStatsRepository.findById(id);
        if(foodStat.isPresent()) return foodStat.get();
        throw new NoSuchElementException("Food stat not found");
    }

    public FoodStatOutputDTO getStatDTO(long id) throws NoSuchElementException {
        var stat = getStat(id);
        log.info(stat.getDate().toString());
        return foodStatMapper.toOutputDTO(stat);
    }

    protected List<FoodStat> getAllStatEntities(){
        return foodStatsRepository.findAll();
    }

    public List<FoodStatOutputDTO> getAllStatsDTO(){
        var stats = getAllStatEntities();
        return stats.stream().map(foodStatMapper::toOutputDTO).sorted(new Comparator<FoodStatOutputDTO>() {
            @Override
            public int compare(FoodStatOutputDTO o1, FoodStatOutputDTO o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        }).toList();
    }

    public List<FoodStatOutputDTO> getUserAllStatsDTO(Long userId) {
        return this.getAllStatsDTO().stream().filter(stat -> stat.getUserId() == userId).toList();
    }

    public List<FoodStatOutputDTOWithoutID> getUserAllStatsWithoutIDDTO(Long userId){
        return this.getUserAllStatsDTO(userId).stream().map(foodStatMapper::toOutputDTOWithoutId).toList();
    }
    protected List<FoodStatOutputDTO> getTodayStatsDTO() {
        var stats = getAllStatEntities();
        int dayOfYearNow = LocalDateTime.now().getDayOfYear();
        int yearNow = LocalDateTime.now().getYear();
        var todayStats = stats.stream()
                .filter(stat -> stat.getDate().getDayOfYear() == dayOfYearNow && stat.getDate().getYear() == yearNow).toList();
        return todayStats.stream().map(foodStatMapper::toOutputDTO).toList();
    }

    public List<DayTotalFoodStats> getAllTodayTotalStats(){
        List<DayTotalFoodStats> dayStats = new ArrayList<>();
        var todayStats = this.getTodayStatsDTO();
        Set<Long> userIds = todayStats.stream().map(FoodStatOutputDTO::getUserId).collect(Collectors.toSet());
        for (Long userId: userIds){
            var todayUserStats = todayStats.stream().filter(stat -> stat.getUserId() == userId).toList();
            dayStats.add(getUserTotalDayStat(todayUserStats, userId));
        }
        return dayStats;
    }

    private DayTotalFoodStats getUserTotalDayStat(List<FoodStatOutputDTO> userStats, long userId) {
        DayTotalFoodStats dayStat = new DayTotalFoodStats();
        dayStat.setFoodStats(userStats.stream().map(foodStatMapper::toOutputDTOWithoutId).toList());

        BigDecimal totalCalories = userStats.stream()
                .map(FoodStatOutputDTO::getCalories)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalProtein = userStats.stream()
                .map(FoodStatOutputDTO::getProtein)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalFat = userStats.stream()
                .map(FoodStatOutputDTO::getFat)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCarbs = userStats.stream()
                .map(FoodStatOutputDTO::getCarbs)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        dayStat.setUserId(userId);
        dayStat.setTotalCalories(totalCalories);
        dayStat.setTotalProtein(totalProtein);
        dayStat.setTotalFat(totalFat);
        dayStat.setTotalCarbs(totalCarbs);

        BigDecimal calorieNorm = Optional.ofNullable(myUserService.getUserCalorieNorm(userId)).orElse(BigDecimal.ZERO);
        dayStat.setMetTheNorm(totalCalories.compareTo(calorieNorm) <= 0);

        return dayStat;
    }

    public DayTotalFoodStats getTodayTotalUserStat(Long userId) throws NoSuchElementException {
        var stats = this.getTodayStatsDTO().stream().filter(stat -> stat.getUserId() == userId).toList();
        if (stats.isEmpty()) throw new NoSuchElementException("Stats not found");
        return getUserTotalDayStat(stats, userId);
    }

    public FoodStatOutputDTO createStat(@Valid FoodStatInputDTO foodStatInputDTO) throws ConstraintViolationException, IllegalArgumentException {

        FoodStat foodStat = getEntityFromInput(foodStatInputDTO);
        this.calculateAndSetFoodArgs(foodStat);
        foodStatsRepository.save(foodStat);
        return this.getStatDTO(foodStat.getId());
    }

    @Transactional
    public List<FoodStatOutputDTO> createStats(List<FoodStatInputDTO> foodStatInputDTOs) throws ConstraintViolationException {
        List<FoodStatOutputDTO> foodStatOutputDTOs = new ArrayList<>();
        for (FoodStatInputDTO foodStatInputDTO : foodStatInputDTOs) {
            foodStatOutputDTOs.add(createStat(foodStatInputDTO));
        }
        return foodStatOutputDTOs;
    }

    @Transactional
    public void deleteStat(long id) throws NoSuchElementException {
        var stat = getStat(id);
        foodStatsRepository.delete(stat);
    }

    private void calculateAndSetFoodArgs(FoodStat foodStat) {

        BigDecimal grams = foodStat.getGrams();
        Dish dish = foodStat.getDish();
        BigDecimal multiplyCoefficient = grams.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_DOWN);

        foodStat.setCalories(dish.getCalories().multiply(multiplyCoefficient));
        foodStat.setProtein(dish.getProtein().multiply(multiplyCoefficient));
        foodStat.setFat(dish.getFat().multiply(multiplyCoefficient));
        foodStat.setCarbs(dish.getCarbs().multiply(multiplyCoefficient));

    }

    private FoodStat getEntityFromInput(FoodStatInputDTO foodStatInputDTO) throws IllegalArgumentException{
        if (foodStatInputDTO == null || foodStatInputDTO.getDishName() == null || foodStatInputDTO.getGrams() == null) {
            throw new IllegalArgumentException("Input data cannot be null");
        }

        FoodStat foodStat = new FoodStat();
        foodStat.setUser(myUserService.getUserById(foodStatInputDTO.getUserId()));
        foodStat.setDish(dishService.getDishByName(foodStatInputDTO.getDishName()));
        foodStat.setStatus(foodStatInputDTO.getStatus());
        foodStat.setGrams(foodStatInputDTO.getGrams());

        return foodStat;
    }



    public List<HistoryDayTotalFoodStats> getUserHistoryTotalStats(long userId) {
        var historyList = new ArrayList<HistoryDayTotalFoodStats>();
        var totalStatsGrouped = this.getUserAllStatsDTO(userId).stream().collect(Collectors.groupingBy(dto -> dto.getDate().toLocalDate()));

        totalStatsGrouped.forEach((date, stats) -> {
            var totalStat = this.getUserTotalDayStat(stats, userId);
            HistoryDayTotalFoodStats dateStat = new HistoryDayTotalFoodStats(totalStat, date);
            log.info(dateStat.toString());
            historyList.add(dateStat);
        });

        return historyList;
    }
}
