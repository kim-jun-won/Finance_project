/**
 * - 뉴스 관련 비즈니스 로직을 처리하는 서비스 계층
 * - 주요 기능:
 *   - saveNews(): 새로운 뉴스 저장
 *   - getAllNews(): 모든 뉴스 조회
 *   - getNewsById(): ID로 특정 뉴스 조회
 * - @Transactional 어노테이션으로 트랜잭션 관리
 * */

package Project.Finacne_News.service.news;

import Project.Finacne_News.domain.News;
import Project.Finacne_News.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NewsService {

    private final NewsRepository newsRepository;

    public Long saveNews(News news) {
        News savedNews = newsRepository.save(news);
        return savedNews.getId();
    }

    @Transactional(readOnly = true)
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<News> getNewsById(Long id) {
        return newsRepository.findById(id);
    }
}
