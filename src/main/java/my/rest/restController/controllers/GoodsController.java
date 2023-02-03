package my.rest.restController.controllers;

import my.rest.restController.entity.Auth;
import my.rest.restController.entity.Goods;
import my.rest.restController.entity.User;
import my.rest.restController.models.Message;
import my.rest.restController.services.AuthService;
import my.rest.restController.services.GoodsService;
import my.rest.restController.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @GetMapping()
    public Object getAll() {
        try{
            var goods = goodsService.readAll();
            return new ResponseEntity<>(goods, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{category}")
    public Object getByCategory(@PathVariable(value = "category") String category) {
        try{
            var goods = goodsService.findByCategory(category);
            return new ResponseEntity<>(goods, HttpStatus.ACCEPTED);
        }catch (Exception e){
            Message msg = new Message("FIND_GOODS_FAILED","Ошибка при поиске товара по категории");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public Object create(@RequestHeader UUID Authorization, @RequestBody Goods goods) {
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

            if(goodsService.create(goods)){
                Message msg = new Message("GOODS_CREATED","Создан товар: " + goods.getName());
                return new ResponseEntity<>(msg, HttpStatus.CREATED);
            }
            else{
                Message msg = new Message("GOODS_CREATE_FAILED","Ошибка при создании товара");
                return new ResponseEntity<>(msg, HttpStatus.OK);
            }
        }catch (Exception e){
            Message msg = new Message("GOODS_CREATE_FAILED","Ошибка при создании товара");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }
}
