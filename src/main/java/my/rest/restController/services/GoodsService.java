package my.rest.restController.services;

import my.rest.restController.entity.Goods;

import java.util.List;

public interface GoodsService {

    boolean create(Goods goods);

    List<Goods> readAll();

    Goods getById(long id);

    List<Goods> findByCategory(String categoryId);
}
