package hanu.gdsc.coreProblem.repositories;

import hanu.gdsc.coreProblem.config.RunningSubmissionConfig;
import hanu.gdsc.coreProblem.domains.RunningSubmission;
import hanu.gdsc.coreProblem.repositories.JPA.RunningSubmissionJPARepository;
import hanu.gdsc.coreProblem.repositories.entities.RunningSubmissionEntity;
import hanu.gdsc.share.domains.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RunningSubmissionRepositoryImpl implements RunningSubmissionRepository {
    @Autowired
    private RunningSubmissionJPARepository runningSubmissionJPARepository;

    @Override
    public void create(RunningSubmission runningSubmission) {
        runningSubmissionJPARepository.save(RunningSubmissionEntity
                .fromDomain(runningSubmission,
                        0,
                        System.currentTimeMillis() - 999999,
                        0));
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public RunningSubmission claim() {
        RunningSubmissionEntity runningSubmission = runningSubmissionJPARepository.claim(System.currentTimeMillis());
        if (runningSubmission == null) {
            return null;
        }
        runningSubmission.setLocked(1);
        runningSubmission.setLockedUntil(System.currentTimeMillis() + RunningSubmissionConfig.CLAIM_SUBMISSION_LOCK_SECOND * 1000);
        runningSubmissionJPARepository.save(runningSubmission);
        return runningSubmission.toDomain();
    }

    @Override
    public void delete(Id id) {
        runningSubmissionJPARepository.deleteById(id.toString());
    }
}
