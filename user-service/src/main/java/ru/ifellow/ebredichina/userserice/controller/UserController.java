package ru.ifellow.ebredichina.userserice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifellow.ebredichina.userserice.dto.GetUserDto;
import ru.ifellow.ebredichina.userserice.service.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
     private final UserService userService;


     @GetMapping
     public GetUserDto getUser (@RequestParam("userName") String userName) {
         return userService.getUser(userName);
     }

     @GetMapping("/ids")
     public GetUserDto getUser (@RequestParam("userId")UUID userId) {
         return userService.getUserById(userId);
     }




}
