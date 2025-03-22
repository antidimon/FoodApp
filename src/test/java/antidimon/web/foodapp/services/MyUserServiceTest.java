package antidimon.web.foodapp.services;

import antidimon.web.foodapp.mappers.MyUserMapper;
import antidimon.web.foodapp.models.dto.user.MyUserEditDTO;
import antidimon.web.foodapp.models.dto.user.MyUserInputDTO;
import antidimon.web.foodapp.models.dto.user.MyUserOutputDTO;
import antidimon.web.foodapp.models.entities.MyUser;
import antidimon.web.foodapp.models.entities.TargetStatus;
import antidimon.web.foodapp.models.entities.UserNorm;
import antidimon.web.foodapp.repositories.MyUserRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MyUserServiceTest {

    @Mock
    private MyUserRepository myUserRepository;

    @Mock
    private UserNormService userNormService;

    @Mock
    private MyUserMapper myUserMapper;

    @InjectMocks
    private MyUserService myUserService;


    private MyUser user1;
    private MyUserInputDTO userInputDTO;
    private MyUserOutputDTO userOutputDTO1;
    private MyUserEditDTO userEditDTO;


    @BeforeEach
    void setUp() {
        user1 = new MyUser();
        user1.setId(1L);
        user1.setName("Test1");
        user1.setEmail("Test1@example.com");
        user1.setAge((short) 28);
        user1.setWeight(new BigDecimal("75.50"));
        user1.setHeight(new BigDecimal("180.00"));
        user1.setStatus(TargetStatus.WEIGHT_GAIN);

        userInputDTO = new MyUserInputDTO();
        userInputDTO.setName("Test1");
        userInputDTO.setEmail("Test1@example.com");
        userInputDTO.setAge((short) 28);
        userInputDTO.setWeight(new BigDecimal("75.50"));
        userInputDTO.setHeight(new BigDecimal("180.00"));
        userInputDTO.setStatus(TargetStatus.WEIGHT_GAIN);

        userOutputDTO1 = new MyUserOutputDTO();
        userOutputDTO1.setId(1L);
        userOutputDTO1.setName("Test1");
        userOutputDTO1.setEmail("Test1@example.com");
        userOutputDTO1.setAge((short) 28);
        userOutputDTO1.setWeight(new BigDecimal("75.50"));
        userOutputDTO1.setHeight(new BigDecimal("180.00"));
        userOutputDTO1.setStatus(TargetStatus.WEIGHT_GAIN);

        userEditDTO = new MyUserEditDTO();
        userEditDTO.setName("Test1 Updated");
        userEditDTO.setEmail("Test1.updated@example.com");
        userEditDTO.setAge((short) 29);
        userEditDTO.setWeight(new BigDecimal("76.00"));
        userEditDTO.setHeight(new BigDecimal("181.00"));
        userEditDTO.setStatus(TargetStatus.MAINTAINING);
    }


    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        when(myUserRepository.findById(1L)).thenReturn(Optional.of(user1));

        MyUser result = myUserService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test1", result.getName());
        verify(myUserRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_ShouldThrowException_WhenUserNotFound() {
        when(myUserRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> myUserService.getUserById(1L));
        verify(myUserRepository, times(1)).findById(1L);
    }


    @Test
    void getUserDTO_ShouldReturnDTO_WhenUserExists() {
        when(myUserRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(myUserMapper.toOutputDTO(user1)).thenReturn(userOutputDTO1);

        MyUserOutputDTO result = myUserService.getUserDTO(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test1", result.getName());
        verify(myUserRepository, times(1)).findById(1L);
        verify(myUserMapper, times(1)).toOutputDTO(user1);
    }

    @Test
    void getUserDTO_ShouldThrowException_WhenUserNotFound() {
        when(myUserRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> myUserService.getUserDTO(1L));
        verify(myUserRepository, times(1)).findById(1L);
    }


    @Test
    void createUser_ShouldReturnDTO_WhenInputIsValid() {
        when(myUserMapper.toEntity(userInputDTO)).thenReturn(user1);
        when(myUserRepository.save(user1)).thenReturn(user1);
        when(myUserMapper.toOutputDTO(user1)).thenReturn(userOutputDTO1);

        MyUserOutputDTO result = myUserService.createUser(userInputDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test1", result.getName());
        verify(myUserMapper, times(1)).toEntity(userInputDTO);
        verify(myUserRepository, times(1)).save(user1);
        verify(userNormService, times(1)).calculateNorms(user1);
        verify(myUserMapper, times(1)).toOutputDTO(user1);
    }

    @Test
    void createUser_ShouldThrowException_WhenInputIsInvalid() {
        when(myUserMapper.toEntity(userInputDTO)).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class, () -> myUserService.createUser(userInputDTO));
        verify(myUserMapper, times(1)).toEntity(userInputDTO);
    }


    @Test
    void editUser_ShouldReturnDTO_WhenInputIsValid() {
        when(myUserRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(myUserRepository.save(user1)).thenReturn(user1);
        when(myUserMapper.toOutputDTO(user1)).thenReturn(userOutputDTO1);

        MyUserOutputDTO result = myUserService.editUser(1L, userEditDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test1", result.getName());
        verify(myUserRepository, times(1)).findById(1L);
        verify(myUserRepository, times(1)).save(user1);
        verify(userNormService, times(2)).calculateNorms(user1);
        verify(myUserMapper, times(1)).toOutputDTO(user1);
    }

    @Test
    void editUser_ShouldThrowException_WhenUserNotFound() {
        when(myUserRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> myUserService.editUser(1L, userEditDTO));
        verify(myUserRepository, times(1)).findById(1L);
    }


    @Test
    void deleteUser_ShouldDeleteUser_WhenUserExists() {
        when(myUserRepository.existsById(1L)).thenReturn(true);

        myUserService.deleteUser(1L);

        verify(myUserRepository, times(1)).existsById(1L);
        verify(myUserRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_ShouldThrowException_WhenUserNotFound() {
        when(myUserRepository.existsById(1L)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> myUserService.deleteUser(1L));
        verify(myUserRepository, times(1)).existsById(1L);
    }


    @Test
    void getUserCalorieNorm_ShouldReturnNorm_WhenUserExists() {
        UserNorm userNorm = new UserNorm();
        userNorm.setCalorieNorm(new BigDecimal("2991.50"));
        user1.setUserNorm(userNorm);

        when(myUserRepository.findById(1L)).thenReturn(Optional.of(user1));

        BigDecimal result = myUserService.getUserCalorieNorm(1L);

        assertNotNull(result);
        assertEquals(new BigDecimal("2991.50"), result);
        verify(myUserRepository, times(1)).findById(1L);
    }

    @Test
    void getUserCalorieNorm_ShouldThrowException_WhenUserNotFound() {
        when(myUserRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> myUserService.getUserCalorieNorm(1L));
        verify(myUserRepository, times(1)).findById(1L);
    }



}
