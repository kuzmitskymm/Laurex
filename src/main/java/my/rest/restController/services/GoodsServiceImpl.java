package my.rest.restController.services;

import my.rest.restController.entity.Goods;
import my.rest.restController.repositories.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService{
    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    public boolean create(Goods goods) {
        return goodsRepository.save(goods) != null;
    }

    @Override
    public List<Goods> readAll() {
        return (List<Goods>) goodsRepository.findAll();
    }

    @Override
    public Goods getById(long id){
        return goodsRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Goods> findByCategory(String categoryId){
        return (List<Goods>) goodsRepository.findByCategoryId(categoryId);
    }
}
