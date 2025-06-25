package Project.Finacne_News.controller;

import Project.Finacne_News.domain.News;
import Project.Finacne_News.repository.NewsRepository;
import Project.Finacne_News.service.news.NewsProcessingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class NewsTestController {

    private final NewsRepository newsRepository;
    private final NewsProcessingService newsProcessingService;

    @PostMapping("/api/test-news")
    @Operation(summary = "뉴스 테스트 처리", description = "본문을 받아 자동 하이라이팅 처리된 결과를 반환합니다.")
    public ResponseEntity<String> testNewsProcess(@RequestBody Map<String, String> body) {
        // 1. 뉴스 객체 생성
        News news = new News();
        news.setTitle("테스트 뉴스");
        news.setContent(body.get("content"));  // ex: "기준금리와 인플레이션이 문제다."
        news.setPublisher("테스트신문");
        news.setPublishedAt(LocalDateTime.now());

        // 2. 저장
        newsRepository.save(news);

        // 3. 자동 처리 (용어 추출 + 하이라이팅)
        newsProcessingService.processNews(news);

        // 4. 처리된 결과 확인
        return ResponseEntity.ok(news.getProcessedContent());
    }
}