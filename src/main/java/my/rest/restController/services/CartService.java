package my.rest.restController.services;

import my.rest.restController.entity.Cart;

import java.util.List;

public interface CartService {

    Cart create(long goods_id, long user_id);

    List<Cart> findCartByUserId(long userId);

    Object changeCount(long id, long userId, int count);

    boolean delete(long cart_id, long user_id);
}
