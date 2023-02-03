package my.rest.restController.controllers;

import my.rest.restController.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping()
    public Object auth(@RequestHeader String login, @RequestHeader String password) {
        try{
            var auth = authService.auth(login, password);
            return new ResponseEntity<>(auth, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/refresh")
    public Object refresh(@RequestHeader UUID refresh) {
        try{
            var auth = authService.refreshToken(refresh);
            return new ResponseEntity<>(auth, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
