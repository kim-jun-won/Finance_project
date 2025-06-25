package Project.Finacne_News.service.highlight;

import Project.Finacne_News.domain.Term;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class HighlightService {

    /**
     * 뉴스 본문에 있는 용어들을 <span> 태그로 감싸서
     * 하이라이팅 + 클릭 시 ID를 추적할 수 있게 만든다.
     */
    public String highlightTerms(String content, List<Term> terms) {
        for (Term term : terms) {
            // \b: 단어 경계, Pattern.quote(): 특수문자 이스케이프
            content = content.replaceAll(
                    "\\b" + Pattern.quote(term.getWord()) + "\\b",
                    "<span class='term' data-term-id='" + term.getId() + "'>" + term.getWord() + "</span>"
            );
        }
        return content;
    }
}
