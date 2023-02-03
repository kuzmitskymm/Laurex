package my.rest.restController.services;

import my.rest.restController.entity.Auth;
import my.rest.restController.entity.User;
import my.rest.restController.repositories.AuthRepository;
import my.rest.restController.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService{
    private final long token_minutes = 60;
    private final long refresh_minutes = 1440;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthRepository authRepository;

    @Override
    public Auth auth(String login, String password) {
        try {
            User user = userRepository.findByLoginAndPassword(login, password);

            if(user != null){
                Auth auth = generateToken(user.getId());
                authRepository.save(auth);
                return auth;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Auth refreshToken(UUID refreshToken){
        try {
            Auth auth = authRepository.findByRefreshToken(refreshToken);

            if (auth != null){
                if(auth.getRefreshTokenExpire().isAfter(OffsetDateTime.now())){
                    Auth answer = generateToken(auth.getUser_id());
                    authRepository.save(answer);
                    return answer;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Auth tokenAlive(UUID accessToken){
        try {
            Auth auth = authRepository.findByAccessToken(accessToken);

            if (auth != null){
                if(auth.getAccessTokenExpire().isAfter(OffsetDateTime.now())){
                    return auth;
                }
                else{
                    return refreshToken(auth.getRefreshToken());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Auth generateToken(long user_id){
        Auth auth = new Auth();
        auth.setUser_id(user_id);
        auth.setAccessToken(UUID.randomUUID());
        auth.setAccessTokenExpire(OffsetDateTime.now().plusMinutes(token_minutes));
        auth.setRefreshToken(UUID.randomUUID());
        auth.setRefreshTokenExpire(OffsetDateTime.now().plusMinutes(refresh_minutes));

        return auth;
    }
}
