package com.ecommerce.user.controller;


import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users") // Base URL
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }


   @GetMapping
    public ResponseEntity<List<UserResponse>> getAllusers(){
        return new ResponseEntity<>(userService.fetchAllUser(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse>  getUser(@PathVariable Long id){
//        User user = userService.fetchUser(id);
//        if(user == null)
//            return ResponseEntity.notFound().build();
//        return ResponseEntity.ok(user);

        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PutMapping("/updateInfo/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id
    ,@RequestBody UserRequest updatedUserRequest){
     boolean updated = userService.updateUser(id,updatedUserRequest);
     if(updated)
         return new ResponseEntity<>("User Updated Successfully",HttpStatus.OK);
     return ResponseEntity.notFound().build();
    }

    @PostMapping
     public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
         userService.addUser(userRequest) ;
         return new ResponseEntity<>("User added Successfully",HttpStatus.OK);
    }


}
