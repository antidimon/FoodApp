package antidimon.web.foodapp.services;


import antidimon.web.foodapp.models.entities.MyUser;
import antidimon.web.foodapp.models.entities.TargetStatus;
import antidimon.web.foodapp.models.entities.UserNorm;
import antidimon.web.foodapp.repositories.MyUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserNormServiceTest {


    @Mock
    private MyUserRepository myUserRepository;

    @InjectMocks
    private UserNormService userNormService;

    private MyUser user;

    @BeforeEach
    void setUp() {
        user = new MyUser();
        user.setId(1L);
        user.setWeight(new BigDecimal("70.00"));
    }

    @Test
    void calculateNorms_ShouldSetWeightLossNorms_WhenStatusIsWeightLoss() {
        user.setStatus(TargetStatus.WEIGHT_LOSS);

        userNormService.calculateNorms(user);

        UserNorm norm = user.getUserNorm();
        assertNotNull(norm);
        assertEquals(new BigDecimal("2010.00"), norm.getCalorieNorm());
        assertEquals(new BigDecimal("176"), norm.getProteinNorm());
        assertEquals(new BigDecimal("56"), norm.getFatNorm());
        assertEquals(new BigDecimal("201"), norm.getCarbsNorm());
        verify(myUserRepository, times(1)).save(user);
    }


    @Test
    void calculateNorms_ShouldSetWeightGainNorms_WhenStatusIsWeightGain() {
        user.setStatus(TargetStatus.WEIGHT_GAIN);

        userNormService.calculateNorms(user);

        UserNorm norm = user.getUserNorm();
        assertNotNull(norm);
        assertEquals(new BigDecimal("2810.00"), norm.getCalorieNorm());
        assertEquals(new BigDecimal("176"), norm.getProteinNorm());
        assertEquals(new BigDecimal("62"), norm.getFatNorm());
        assertEquals(new BigDecimal("386"), norm.getCarbsNorm());
        verify(myUserRepository, times(1)).save(user);
    }

    @Test
    void calculateNorms_ShouldSetMaintainingNorms_WhenStatusIsMaintaining() {
        user.setStatus(TargetStatus.MAINTAINING);

        userNormService.calculateNorms(user);

        UserNorm norm = user.getUserNorm();
        assertNotNull(norm);
        assertEquals(new BigDecimal("2310.00"), norm.getCalorieNorm());
        assertEquals(new BigDecimal("127"), norm.getProteinNorm());
        assertEquals(new BigDecimal("67"), norm.getFatNorm());
        assertEquals(new BigDecimal("300"), norm.getCarbsNorm());
        verify(myUserRepository, times(1)).save(user);
    }

    @Test
    void calculateNorms_ShouldSetRecompositionNorms_WhenStatusIsRecomposition() {
        user.setStatus(TargetStatus.RECOMPOSITION);

        userNormService.calculateNorms(user);

        UserNorm norm = user.getUserNorm();
        assertNotNull(norm);
        assertEquals(new BigDecimal("2310.00"), norm.getCalorieNorm());
        assertEquals(new BigDecimal("173"), norm.getProteinNorm());
        assertEquals(new BigDecimal("62"), norm.getFatNorm());
        assertEquals(new BigDecimal("266"), norm.getCarbsNorm());
        verify(myUserRepository, times(1)).save(user);
    }

    @Test
    void calculateNorms_ShouldThrowException_WhenStatusIsUnknown() {
        user.setStatus(null);

        assertThrows(IllegalStateException.class, () -> userNormService.calculateNorms(user));
        verify(myUserRepository, never()).save(user);
    }

    @Test
    void calculateNorms_ShouldHandleZeroWeight_WhenWeightIsZero() {
        user.setWeight(BigDecimal.ZERO);
        user.setStatus(TargetStatus.MAINTAINING);

        userNormService.calculateNorms(user);

        UserNorm norm = user.getUserNorm();
        assertNotNull(norm);
        assertEquals(BigDecimal.ZERO, norm.getCalorieNorm());
        assertEquals(BigDecimal.ZERO, norm.getProteinNorm());
        assertEquals(BigDecimal.ZERO, norm.getFatNorm());
        assertEquals(BigDecimal.ZERO, norm.getCarbsNorm());
        verify(myUserRepository, times(1)).save(user);
    }


}
