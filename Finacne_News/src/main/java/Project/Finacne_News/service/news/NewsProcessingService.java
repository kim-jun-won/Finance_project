package Project.Finacne_News.service.news;

import Project.Finacne_News.domain.News;
import Project.Finacne_News.domain.Term;
import Project.Finacne_News.service.term.TermProcessingService;
import Project.Finacne_News.service.highlight.HighlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NewsProcessingService {

    private final TermProcessingService termProcessingService;
    private final HighlightService highlightService;

    /**
     * 뉴스 본문을 처리하여:
     * - 용어 추출 및 저장 (DB or API)
     * - 본문에 하이라이팅 적용
     * - 하이라이팅된 결과를 뉴스 객체에 저장
     */
    public void processNews(News news) {
        // 1. 뉴스 본문에서 용어 추출 (DB + API 연동)
        List<Term> terms = termProcessingService.findOrFetchTermsFromContent(news.getContent());

        // 2. 본문 하이라이팅 처리
        String highlighted = highlightService.highlightTerms(news.getContent(), terms);

        // 3. 하이라이팅된 본문 저장
        news.setProcessedContent(highlighted);
    }
}
