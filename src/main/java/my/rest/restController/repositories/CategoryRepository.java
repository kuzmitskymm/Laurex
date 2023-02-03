package my.rest.restController.repositories;

import my.rest.restController.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category,String> {
}
