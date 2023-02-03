package my.rest.restController.controllers;

import my.rest.restController.configs.Config;
import my.rest.restController.entity.Auth;
import my.rest.restController.entity.User;
import my.rest.restController.models.Message;
import my.rest.restController.services.AuthService;
import my.rest.restController.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping()
    public Object createUser(@RequestHeader String Authorization, @RequestBody User user) {
        try{
            if(!Authorization.equals(Config.api_key)){
                Message msg = new Message("FORBIDDEN", "Авторизация прошла с ошибкой.");
                return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
            }

            userService.create(user);
            return new ResponseEntity<>(user,HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public Object getUser(@RequestHeader UUID Authorization) {
        try{
            Auth auth = authService.tokenAlive(Authorization);
            if(auth == null){
                Message msg = new Message("AUTHORIZATION_FAILED", "Авторизация прошла с ошибкой. Новый тикет не получилось создать.");
                return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
            }

            var user = userService.getUser(auth.getUser_id());
            return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
