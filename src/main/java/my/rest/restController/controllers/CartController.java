package my.rest.restController.controllers;

import my.rest.restController.entity.Auth;
import my.rest.restController.entity.Cart;
import my.rest.restController.models.Message;
import my.rest.restController.services.AuthService;
import my.rest.restController.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private AuthService authService;

    @GetMapping()
    public Object getAll(@RequestHeader UUID Authorization) {
        try{
            Auth auth = authService.tokenAlive(Authorization);
            if(auth == null){
                Message msg = new Message("AUTHORIZATION_FAILED", "Авторизация прошла с ошибкой. Новый тикет не получилось создать.");
                return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
            }

            var carts = cartService.findCartByUserId(auth.getUser_id());
            return new ResponseEntity<>(carts, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add/{goods_id}")
    public Object createCart(@RequestHeader UUID Authorization, @PathVariable(value = "goods_id") long goods_id) {
        try{
            Auth auth = authService.tokenAlive(Authorization);
            if(auth == null){
                Message msg = new Message("AUTHORIZATION_FAILED", "Авторизация прошла с ошибкой. Новый тикет не получилось создать.");
                return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
            }

            Cart answer = cartService.create(goods_id, auth.getUser_id());
            if(answer != null){
                return new ResponseEntity<>(answer, HttpStatus.OK);
            }
            else{
                Message msg = new Message("CART_CREATED_FAILED", "Ошибка при добавлении товара в корзину.");
                return new ResponseEntity<>(msg, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/plusCount")
    public Object plusCount(@RequestHeader UUID Authorization, @PathVariable(value = "id") long id) {
        try{
            Auth auth = authService.tokenAlive(Authorization);
            if(auth == null){
                Message msg = new Message("AUTHORIZATION_FAILED", "Авторизация прошла с ошибкой. Новый тикет не получилось создать.");
                return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
            }

            return cartService.changeCount(id, auth.getUser_id(), 1);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/minusCount")
    public Object minusCount(@RequestHeader UUID Authorization, @PathVariable(value = "id") long id) {
        try{
            Auth auth = authService.tokenAlive(Authorization);
            if(auth == null){
                Message msg = new Message("AUTHORIZATION_FAILED", "Авторизация прошла с ошибкой. Новый тикет не получилось создать.");
                return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
            }

            return cartService.changeCount(id, auth.getUser_id(), -1);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public Object deleteCart(@RequestHeader UUID Authorization, @PathVariable(value = "id") long id) {
        try{
            Auth auth = authService.tokenAlive(Authorization);
            if(auth == null){
                Message msg = new Message("AUTHORIZATION_FAILED", "Авторизация прошла с ошибкой. Новый тикет не получилось создать.");
                return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
            }

            if(cartService.delete(id, auth.getUser_id())){
                return new ResponseEntity<>(id, HttpStatus.OK);
            }
            else{
                Message msg = new Message("CART_DELETED_FAILED", "Удаление товара из корзины прошло с ошибкой.");
                return new ResponseEntity<>(msg, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
