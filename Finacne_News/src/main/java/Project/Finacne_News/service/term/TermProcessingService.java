package Project.Finacne_News.service.term;

import Project.Finacne_News.client.KsdDictionaryClient;
import Project.Finacne_News.domain.Term;
import Project.Finacne_News.dto.FinancialTermDTO;
import Project.Finacne_News.repository.TermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TermProcessingService {

    private final TermRepository termRepository;
    private final KsdDictionaryClient ksdClient;

    /**
     * 뉴스 본문에서 단어들을 추출한 후
     * - DB에 있는 용어는 그대로 사용하고,
     * - 없는 용어는 OpenAPI를 호출하여 설명을 받아온 후 저장한다.
     */
    public List<Term> findOrFetchTermsFromContent(String content) {
        Set<String> words = extractWords(content); // 단어 추출

        Map<String, Term> termMap = new HashMap<>();

        // 1. DB에서 이미 등록된 용어 조회
        List<Term> existing = termRepository.findByWordIn(words);
        existing.forEach(term -> termMap.put(term.getWord(), term));

        // 2. 없는 단어는 API로 조회 → 저장
        for (String word : words) {
            if (!termMap.containsKey(word)) {
                ksdClient.searchTerm(word).ifPresent(dto -> {
                    Term newTerm = new Term();
                    newTerm.setWord(dto.getWord());
                    newTerm.setDescription(dto.getDescription());
                    termMap.put(word, termRepository.save(newTerm));
                });
            }
        }

        return new ArrayList<>(termMap.values());
    }

    /**
     * 뉴스 본문에서 한글, 숫자, 영어를 공백 기준으로 분리하여 단어 집합 추출
     */
    private Set<String> extractWords(String content) {
        content = content.replaceAll("[^가-힣a-zA-Z0-9\\s]", " ");  // 특수문자 제거
        return new HashSet<>(Arrays.asList(content.split("\\s+"))); // 공백 기준 분리
    }
}
