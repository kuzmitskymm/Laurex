package my.rest.restController.services;

import my.rest.restController.entity.User;
import my.rest.restController.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean create(User user) {
        User answer = null;
        try {
            answer = userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answer == null ? false : true;
    }

    @Override
    public User getUser(long id){
        return userRepository.findById(id).orElseThrow();
    }
}
