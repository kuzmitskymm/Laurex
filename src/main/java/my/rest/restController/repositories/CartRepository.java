package my.rest.restController.repositories;

import my.rest.restController.entity.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartRepository extends CrudRepository<Cart,Long> {
    List<Cart> findByUserId(long userId);

    Cart findByUserIdAndId(long user_id, long id);
}
