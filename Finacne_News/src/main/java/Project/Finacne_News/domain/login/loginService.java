package Project.Finacne_News.domain.login;

import Project.Finacne_News.domain.User;
import Project.Finacne_News.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class loginService {
    private final UserRepository userRepository;

    public User login(String loginId, String password){
        return userRepository.findByLoginId(loginId)
                .filter(user -> user.getPassword().equals(password))
                .orElse(null);
    }
}
