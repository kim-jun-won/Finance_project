package Project.Finacne_News.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class FinancialTermDTO {
    private String word;           // 용어
    private String description;    // 용어 설명
    private String category;       // 용어 분류
    private String source;         // 출처

    // 응답 구조에 맞게 필드 추가 가능
    // KSD API 응답 구조에 맞는 내부 클래스나 추가 필드 정의
}
