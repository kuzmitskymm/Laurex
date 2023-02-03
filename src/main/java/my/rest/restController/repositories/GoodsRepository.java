package my.rest.restController.repositories;

import my.rest.restController.entity.Goods;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GoodsRepository extends CrudRepository<Goods,Long> {

    List<Goods> findByCategoryId(String categoryId);
}
