package antidimon.web.foodapp.services;

import antidimon.web.foodapp.models.entities.MyUser;
import antidimon.web.foodapp.models.entities.UserNorm;
import antidimon.web.foodapp.repositories.MyUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@AllArgsConstructor
public class UserNormService {

    private static final BigDecimal caloriesPerKgCoefficient = new BigDecimal(33);

    public static final BigDecimal caloriesInProtein = new BigDecimal(4);
    public static final BigDecimal caloriesInFat = new BigDecimal(9);
    public static final BigDecimal caloriesInCarb = new BigDecimal(4);

    private static final BigDecimal weightLossProteinPercentage = new BigDecimal("0.35");
    private static final BigDecimal weightLossFatPercentage = new BigDecimal("0.25");
    private static final BigDecimal weightLossCarbsPercentage = new BigDecimal("0.40");

    private static final BigDecimal weightGainProteinPercentage = new BigDecimal("0.25");
    private static final BigDecimal weightGainFatPercentage = new BigDecimal("0.20");
    private static final BigDecimal weightGainCarbsPercentage = new BigDecimal("0.55");

    private static final BigDecimal maintainingProteinPercentage = new BigDecimal("0.22");
    private static final BigDecimal maintainingFatPercentage = new BigDecimal("0.26");
    private static final BigDecimal maintainingCarbsPercentage = new BigDecimal("0.52");

    private static final BigDecimal recompositionProteinPercentage = new BigDecimal("0.30");
    private static final BigDecimal recompositionFatPercentage = new BigDecimal("0.24");
    private static final BigDecimal recompositionCarbsPercentage = new BigDecimal("0.46");


    private final MyUserRepository myUserRepository;

    @Transactional
    public void calculateNorms(MyUser user) throws IllegalArgumentException {

        if (user.getStatus() == null) {
            throw new IllegalStateException("User status cannot be null");
        }

        switch (user.getStatus()){
            case WEIGHT_LOSS -> {
                this.setWeightLossNorms(user);
            }
            case WEIGHT_GAIN -> {
                this.setWeightGainNorms(user);
            }
            case MAINTAINING -> {
                this.setMaintainingNorms(user);
            }
            case RECOMPOSITION -> {
                this.setRecompositionNorms(user);
            }
            default -> {
                throw new IllegalStateException("Unexpected value: " + user.getStatus());
            }
        }
    }


    protected void setWeightLossNorms(MyUser user) {
        BigDecimal RSK = user.getWeight().multiply(caloriesPerKgCoefficient).add(new BigDecimal(-300));
        createNorm(user, RSK, weightLossProteinPercentage, weightLossFatPercentage, weightLossCarbsPercentage);
    }

    protected void setWeightGainNorms(MyUser user) {
        BigDecimal RSK = user.getWeight().multiply(caloriesPerKgCoefficient).add(new BigDecimal(+500));
        createNorm(user, RSK, weightGainProteinPercentage, weightGainFatPercentage, weightGainCarbsPercentage);
    }

    protected void setMaintainingNorms(MyUser user) {
        BigDecimal RSK = user.getWeight().multiply(caloriesPerKgCoefficient);
        createNorm(user, RSK, maintainingProteinPercentage, maintainingFatPercentage, maintainingCarbsPercentage);
    }

    protected void setRecompositionNorms(MyUser user) {
        BigDecimal RSK = user.getWeight().multiply(caloriesPerKgCoefficient);
        createNorm(user, RSK, recompositionProteinPercentage, recompositionFatPercentage, recompositionCarbsPercentage);
    }

    private void createNorm(MyUser user, BigDecimal RSK, BigDecimal proteinPercent, BigDecimal fatPercent, BigDecimal carbsPercent) {
        UserNorm userNorm = UserNorm.builder()
                .user(user)
                .calorieNorm(RSK)
                .proteinNorm(RSK.multiply(proteinPercent).divide(caloriesInProtein, 0, RoundingMode.HALF_DOWN))
                .fatNorm(RSK.multiply(fatPercent).divide(caloriesInFat, 0, RoundingMode.HALF_DOWN))
                .carbsNorm(RSK.multiply(carbsPercent).divide(caloriesInCarb, 0, RoundingMode.HALF_DOWN))
                .build();

        userNorm.setUser(user);
        userNorm.setUserId(user.getId());
        user.setUserNorm(userNorm);
        myUserRepository.save(user);
    }
}
