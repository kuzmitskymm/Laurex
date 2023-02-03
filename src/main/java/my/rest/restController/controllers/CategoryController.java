package my.rest.restController.controllers;

import my.rest.restController.entity.Auth;
import my.rest.restController.entity.Category;
import my.rest.restController.entity.User;
import my.rest.restController.models.Message;
import my.rest.restController.services.AuthService;
import my.rest.restController.services.CategoryService;
import my.rest.restController.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @GetMapping()
    public Object getAll() {
        try{
            var category = categoryService.readAll();
            return new ResponseEntity<>(category, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public Object create(@RequestHeader UUID Authorization, @RequestBody Category category) {
        try{
            Auth auth = authService.tokenAlive(Authorization);
            if(auth == null){
                Message msg = new Message("AUTHORIZATION_FAILED", "Авторизация прошла с ошибкой. Новый тикет не получилось создать.");
                return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
            }

            User user = userService.getUser(auth.getUser_id());
            if(!user.isAdmin()){
                Message msg = new Message("FORBIDDEN", "Недостаточно полномочий для этой операции.");
                return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
            }

            if(categoryService.create(category)){
                Message msg = new Message("CATEGORY_CREATED","Создана категория: " + category.getName());
                return new ResponseEntity<>(msg, HttpStatus.CREATED);
            }
            else{
                Message msg = new Message("CATEGORY_CREATE_FAILED","Ошибка при создании категории");
                return new ResponseEntity<>(msg, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
