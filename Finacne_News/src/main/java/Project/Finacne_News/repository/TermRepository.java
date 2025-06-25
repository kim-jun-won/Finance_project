package Project.Finacne_News.repository;

import Project.Finacne_News.domain.Term;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TermRepository extends JpaRepository<Term, Long> {
    Optional<Term> findByWord(String word);
    List<Term> findByWordIn(Collection<String> words);
    boolean existsByWord(String word);
    List<Term> findByCategory(String category);
}
