package antidimon.web.foodapp.controllers;


import antidimon.web.foodapp.models.dto.user.MyUserEditDTO;
import antidimon.web.foodapp.models.dto.user.MyUserInputDTO;
import antidimon.web.foodapp.models.dto.user.MyUserOutputDTO;
import antidimon.web.foodapp.services.MyUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Контроллер пользователей", description = "Работа с сущностью пользователя")
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class MyUserController {

    private MyUserService myUserService;

    @Operation
    @GetMapping
    public ResponseEntity<List<MyUserOutputDTO>> getUsers(){
        var users = myUserService.getUsersDTO();
        return ResponseEntity.ok(users);
    }

    @Operation
    @GetMapping("/{userId}")
    public ResponseEntity<MyUserOutputDTO> getUserById(@PathVariable(name = "userId") long userId){
        var user = myUserService.getUserDTO(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Operation
    @PostMapping
    public ResponseEntity<MyUserOutputDTO> createUser(@RequestBody MyUserInputDTO myUserInputDTO){
        var user = myUserService.createUser(myUserInputDTO);
        return ResponseEntity.ok(user);
    }

    @Operation
    @PatchMapping("/{userId}")
    public ResponseEntity<MyUserOutputDTO> updateUser(@PathVariable(name = "userId") long userId, @RequestBody MyUserEditDTO myUserEditDTO){
        var user = myUserService.editUser(userId, myUserEditDTO);
        return ResponseEntity.ok(user);
    }

    @Operation
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "userId") long userId){
        myUserService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }


}
