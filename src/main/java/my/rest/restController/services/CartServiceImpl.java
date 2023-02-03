package my.rest.restController.services;

import my.rest.restController.entity.Cart;
import my.rest.restController.entity.Goods;
import my.rest.restController.entity.User;
import my.rest.restController.models.Message;
import my.rest.restController.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;

    @Override
    public Cart create(long goods_id, long user_id) {
        User user = userService.getUser(user_id);
        Goods goods = goodsService.getById(goods_id);

        Cart cart = new Cart();
        cart.setGoodsId(goods_id);
        cart.setName(goods.getName());
        cart.setPrice(goods.getPrice());
        cart.setCount(1);
        cart.setDescription(goods.getDescription());
        cart.setCategory(goods.getCategory());
        cart.setUser(user);

        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> findCartByUserId(long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Object changeCount(long id, long userId, int count) {
        Cart cart = cartRepository.findByUserIdAndId(id, userId);

        if(cart != null){
            int new_count = cart.getCount() + count;
            if(new_count < 1){
                return new Message("INCORRECT_COUNT", "Количество не может быть меньше 1.");
            }

            cart.setCount(new_count);
            cartRepository.save(cart);
            return cart;
        }
        else {
            return new Message("NOT_FOUND", "Товар с id=" + id + " в корзине не найден.");
        }
    }

    @Override
    public boolean delete(long cart_id, long user_id) {
        try {
            Cart cart = cartRepository.findByUserIdAndId(user_id, cart_id);

            if(cart != null){
                cartRepository.deleteById(cart_id);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
