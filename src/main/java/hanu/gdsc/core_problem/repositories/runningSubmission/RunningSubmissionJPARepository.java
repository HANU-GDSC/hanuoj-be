package hanu.gdsc.core_problem.repositories.runningSubmission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RunningSubmissionJPARepository extends JpaRepository<RunningSubmissionEntity, String> {
    @Query(value = "SELECT * FROM core_problem_running_submission u " +
            "WHERE ( (u.locked = 0) or (u.locked_until < :currentMillis) ) " +
            "ORDER BY submitted_at DESC LIMIT 1",
            nativeQuery = true)
    public RunningSubmissionEntity claim(@Param("currentMillis") long currentMillis);

    public RunningSubmissionEntity findByIdAndCoderIdAndServiceToCreate(String id, String coderId, String serviceToCreate);

    public Page<RunningSubmissionEntity> findByCoderIdAndServiceToCreate(String coderId, String serviceToCreate, Pageable pageable);
}
