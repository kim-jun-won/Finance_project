package Project.Finacne_News;

import Project.Finacne_News.domain.User;
import Project.Finacne_News.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final UserRepository userRepository;

    /**
     * 테스트용 데이터 추가
     * */
    @PostConstruct
    public void init(){
        User user = new User();

        user.setLoginId("1234");
        user.setPassword("1234");
        user.setNickname("시스");
        user.setName("김준원");

        userRepository.save(user);
    }
}
