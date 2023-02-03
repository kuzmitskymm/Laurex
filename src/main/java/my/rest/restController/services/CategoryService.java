package my.rest.restController.services;

import my.rest.restController.entity.Category;

import java.util.List;

public interface CategoryService {

    boolean create(Category category);

    List<Category> readAll();
}
