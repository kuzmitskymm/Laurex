package my.rest.restController.services;

import my.rest.restController.entity.User;

import java.util.Optional;

public interface UserService {

    boolean create(User user);

    User getUser(long id);
}
