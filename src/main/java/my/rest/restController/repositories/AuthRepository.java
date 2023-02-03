package my.rest.restController.repositories;

import my.rest.restController.entity.Auth;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AuthRepository extends CrudRepository<Auth,Long> {

    Auth findByAccessToken(UUID accessToken);
    Auth findByRefreshToken(UUID refreshToken);
}
