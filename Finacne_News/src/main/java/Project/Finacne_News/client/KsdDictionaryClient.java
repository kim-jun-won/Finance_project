package Project.Finacne_News.client;

import Project.Finacne_News.dto.FinancialTermDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
@Slf4j
public class KsdDictionaryClient {

    @Value("${ksd.api.key}") // application.yml에 인코딩되지 않은 키를 넣을 것
    private String apiKey;

    private final RestTemplate restTemplate;

    public KsdDictionaryClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<FinancialTermDTO> searchTerm(String word) {
        try {
            // 1. 검색어만 인코딩
            String encodedWord = URLEncoder.encode(word, StandardCharsets.UTF_8);


            StringBuilder urlBuilder = new StringBuilder("http://api.seibro.or.kr/openapi/service/FnTermSvc/getFinancialTermMeaning");
            urlBuilder.append("?serviceKey=").append(apiKey); // 🔥 원본 그대로
            urlBuilder.append("&term=").append(encodedWord);
            urlBuilder.append("&pageNo=1");
            urlBuilder.append("&numOfRows=1");

            URI uri = new URI(urlBuilder.toString());

            log.info("📡 Request URI: {}", uri);

            // 3. 헤더 설정 (Content-type만 application/json → 실제는 application/xml 받아야 안전)
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/xml");

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            // 4. 요청 수행
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);

            String xmlResponse = response.getBody();
            log.debug("📦 XML Response: {}", xmlResponse);

            // 5. 이후 XML → DTO 파싱 로직 필요
            return Optional.empty();

        } catch (Exception e) {
            log.error("❌ Failed to fetch term: {}", word, e);
            return Optional.empty();
        }
    }
}
