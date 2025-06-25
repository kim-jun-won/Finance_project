/**
 * - REST API 엔드포인트 제공
 * - Swagger 문서화 적용
 * - 주요 엔드포인트:
 *   - POST /api/news: 새 뉴스 등록
 *   - GET /api/news: 모든 뉴스 조회
 *   - GET /api/news/{id}: 특정 뉴스 조회
 * - 각 API에 대한 상세한 설명과 응답 코드 문서화
 *
 * */


package Project.Finacne_News.controller;

import Project.Finacne_News.domain.News;
import Project.Finacne_News.dto.NewsRequestDto;
import Project.Finacne_News.dto.NewsResponseDto;
import Project.Finacne_News.service.news.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@Tag(name = "뉴스 API", description = "뉴스 관리를 위한 API")
public class NewsController {

    private final NewsService newsService;

    @Operation(summary = "뉴스 등록", description = "새로운 뉴스 기사를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "뉴스 등록 성공",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping
    public ResponseEntity<Long> createNews(@RequestBody NewsRequestDto newsRequest) {
        News news = new News();
        news.setTitle(newsRequest.getTitle());
        news.setContent(newsRequest.getContent());
        news.setPublisher(newsRequest.getPublisher());
        news.setPublishedAt(newsRequest.getPublishedAt());

        Long savedId = newsService.saveNews(news);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedId);
    }

    @Operation(summary = "뉴스 전체 조회", description = "모든 뉴스 기사를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<NewsResponseDto>> getAllNews() {
        List<News> newsList = newsService.getAllNews();
        List<NewsResponseDto> responseDtos = newsList.stream()
                .map(news -> NewsResponseDto.builder()
                        .id(news.getId())
                        .title(news.getTitle())
                        .content(news.getContent())
                        .publisher(news.getPublisher())
                        .publishedAt(news.getPublishedAt())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    @Operation(summary = "뉴스 상세 조회", description = "특정 ID의 뉴스 기사를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<NewsResponseDto> getNewsById(@PathVariable Long id) {
        Optional<News> newsOptional = newsService.getNewsById(id);
        if (newsOptional.isPresent()) {
            News news = newsOptional.get();
            NewsResponseDto responseDto = NewsResponseDto.builder()
                    .id(news.getId())
                    .title(news.getTitle())
                    .content(news.getContent())
                    .publisher(news.getPublisher())
                    .publishedAt(news.getPublishedAt())
                    .build();
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
