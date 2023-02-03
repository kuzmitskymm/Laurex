package my.rest.restController.services;

import my.rest.restController.entity.Category;
import my.rest.restController.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean create(Category category) {
        return categoryRepository.save(category) != null;
    }

    @Override
    public List<Category> readAll() {
        return (List<Category>) categoryRepository.findAll();
    }
}
