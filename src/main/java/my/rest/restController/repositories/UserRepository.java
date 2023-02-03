package my.rest.restController.repositories;

import my.rest.restController.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

    User findByLoginAndPassword(String login, String password);
}
