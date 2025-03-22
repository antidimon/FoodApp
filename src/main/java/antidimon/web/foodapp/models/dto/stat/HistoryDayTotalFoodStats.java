package antidimon.web.foodapp.models.dto.stat;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class HistoryDayTotalFoodStats {

    private LocalDate date;
    private DayTotalFoodStats dayTotalFoodStats;

    public HistoryDayTotalFoodStats(DayTotalFoodStats userTotalDayStat, LocalDate date) {
        this.date = date;
        this.dayTotalFoodStats = userTotalDayStat;
    }
}
