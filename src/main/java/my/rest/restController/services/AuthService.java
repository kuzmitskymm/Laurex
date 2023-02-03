package my.rest.restController.services;

import my.rest.restController.entity.Auth;

import java.util.UUID;

public interface AuthService {

    Auth auth(String login, String password);

    Auth refreshToken(UUID refreshToken);

    Auth tokenAlive(UUID accessToken);
}
