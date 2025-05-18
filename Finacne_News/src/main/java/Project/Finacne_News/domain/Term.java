package Project.Finacne_News.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@Table(name = "terms")
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "term_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String word;

    private Integer frequency;

    private String category;

    // === 연관관계 ===

    @OneToMany(mappedBy = "term", cascade = CascadeType.ALL)
    private List<Glossary> glossaries;

    @OneToMany(mappedBy = "term", cascade = CascadeType.ALL)
    private List<UserVocabulary> userVocabularies;

    @OneToMany(mappedBy = "term", cascade = CascadeType.ALL)
    private List<QuizTerm> quizTerms;

}
