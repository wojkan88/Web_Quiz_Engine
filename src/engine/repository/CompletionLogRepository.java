package engine.repository;

import engine.model.CompletionLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletionLogRepository extends PagingAndSortingRepository<CompletionLog, Long> {

    Page<CompletionLog> findAllByUsername(String username, Pageable pageable);
}
