package antidimon.web.foodapp.services;

import antidimon.web.foodapp.mappers.MyUserMapper;
import antidimon.web.foodapp.models.dto.user.MyUserEditDTO;
import antidimon.web.foodapp.models.dto.user.MyUserInputDTO;
import antidimon.web.foodapp.models.dto.user.MyUserOutputDTO;
import antidimon.web.foodapp.models.entities.MyUser;
import antidimon.web.foodapp.repositories.MyUserRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@AllArgsConstructor
@Validated
public class MyUserService {

    private MyUserRepository myUserRepository;
    private MyUserMapper myUserMapper;
    private UserNormService userNormService;

    protected MyUser getUserById(long id) throws NoSuchElementException {
        Optional<MyUser> myUser = myUserRepository.findById(id);
        if (myUser.isPresent()) return myUser.get();
        throw new NoSuchElementException("User not found");
    }

    public MyUserOutputDTO getUserDTO(long id) throws NoSuchElementException {
        return myUserMapper.toOutputDTO(getUserById(id));
    }

    protected List<MyUser> getUsers(){
        return myUserRepository.findAll();
    }

    public List<MyUserOutputDTO> getUsersDTO(){
        var users = this.getUsers();
        return users.stream().map(myUserMapper::toOutputDTO).toList();
    }


    @Transactional
    public MyUserOutputDTO createUser(@Valid MyUserInputDTO myUserInputDTO) throws ConstraintViolationException, IllegalArgumentException {
        MyUser user = myUserMapper.toEntity(myUserInputDTO);
        myUserRepository.save(user);
        this.userNormService.calculateNorms(user);
        return myUserMapper.toOutputDTO(user);
    }

    @Transactional
    public MyUserOutputDTO editUser(long id, MyUserEditDTO myUserEditDTO) throws NoSuchElementException {
        MyUser user = getUserById(id);


        if (myUserEditDTO.getName() != null && !myUserEditDTO.getName().isBlank()) user.setName(myUserEditDTO.getName());

        List<String> errors = this.validateOtherData(myUserEditDTO, user);

        if (!errors.isEmpty()) throw new IllegalArgumentException(String.join("\n", errors));

        if (myUserEditDTO.getEmail() != null) user.setEmail(myUserEditDTO.getEmail());
        if (myUserEditDTO.getAge() != null) user.setAge(myUserEditDTO.getAge());
        if (myUserEditDTO.getWeight() != null) {
            user.setWeight(myUserEditDTO.getWeight());
            userNormService.calculateNorms(user);
        }
        if (myUserEditDTO.getHeight() != null) user.setHeight(myUserEditDTO.getHeight());
        if (myUserEditDTO.getStatus() != null && !myUserEditDTO.getStatus().equals(user.getStatus())) {
            user.setStatus(myUserEditDTO.getStatus());
            userNormService.calculateNorms(user);
        }

        myUserRepository.save(user);
        return myUserMapper.toOutputDTO(user);
    }

    private List<String> validateOtherData(MyUserEditDTO dto, MyUser user) {

        List<String> errors = new ArrayList<>();

        if (dto.getEmail() != null) errors.addAll(validateEmail(dto.getEmail(), user));
        if (dto.getAge() != null) errors.addAll(validateAge(dto.getAge()));
        if (dto.getWeight() != null) errors.addAll(validateWeight(dto.getWeight()));
        if (dto.getHeight() != null) errors.addAll(validateHeight(dto.getHeight()));

        return errors;
    }

    @Transactional
    public void deleteUser(long id) throws NoSuchElementException {
        if (!myUserRepository.existsById(id))throw new NoSuchElementException("User not found");
        myUserRepository.deleteById(id);
    }



    private List<String> validateEmail(String email, MyUser user) {
        List<String> errors = new ArrayList<>();
        if (!EmailValidator.getInstance().isValid(email)) {
            errors.add("Invalid email");
        }
        if (!user.getEmail().equals(email) &&
                myUserRepository.findByEmail(email).isPresent()) {
            errors.add("Email is in use");
        }
        return errors;
    }

    private List<String> validateAge(Short age) {
        List<String> errors = new ArrayList<>();
        if (age < 0 || age > 100) {
            errors.add("Age must be > 0 and < 100");
        }
        return errors;
    }

    private List<String> validateWeight(BigDecimal weight) {
        List<String> errors = new ArrayList<>();
        if (weight.compareTo(BigDecimal.ZERO) < 0 ||
                weight.compareTo(BigDecimal.valueOf(350)) > 0) {
            errors.add("Weight must be > 0 and < 350");
        }
        return errors;
    }

    private List<String> validateHeight(BigDecimal height) {
        List<String> errors = new ArrayList<>();
        if (height.compareTo(BigDecimal.ZERO) < 0 ||
                height.compareTo(BigDecimal.valueOf(350)) > 0) {
            errors.add("Height must be > 0 and < 350");
        }
        return errors;
    }

    public BigDecimal getUserCalorieNorm(long userId) {
        var user = this.getUserById(userId);
        return user.getUserNorm().getCalorieNorm();
    }
}


