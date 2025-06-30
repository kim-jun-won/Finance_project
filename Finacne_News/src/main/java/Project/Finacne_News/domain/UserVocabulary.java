package Project.Finacne_News.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
@Table(name = "user_vocabulary")
public class UserVocabulary {
    /** user와 term를 N:M 연결하기 위한 중간 객체 **/
    // 동시에 사용자가 등록한 모르는 단어 관리

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_vocabulary_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "term_id", nullable = false)
    private Term term;

    @Column(name = "familiarity_level")
    private Integer familiarityLevel;

    @Column(name = "last_seen")
    private LocalDate lastSeen;
}
