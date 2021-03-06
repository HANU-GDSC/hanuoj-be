package hanu.gdsc.core_problem.repositories.runningSubmission;

import hanu.gdsc.core_problem.config.RunningSubmissionConfig;
import hanu.gdsc.core_problem.domains.RunningSubmission;
import hanu.gdsc.share.domains.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RunningSubmissionRepositoryImpl implements RunningSubmissionRepository {
    @Autowired
    private RunningSubmissionJPARepository runningSubmissionJPARepository;

    @Override
    public void create(RunningSubmission runningSubmission) {
        runningSubmissionJPARepository.save(RunningSubmissionEntity
                .fromDomain(runningSubmission,
                        0,
                        System.currentTimeMillis() - 999999));
    }

    private long getLockedUntil() {
        return System.currentTimeMillis() + RunningSubmissionConfig.CLAIM_SUBMISSION_LOCK_SECOND * 1000;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public RunningSubmission claim() {
        RunningSubmissionEntity runningSubmission = runningSubmissionJPARepository.claim(System.currentTimeMillis());
        if (runningSubmission == null) {
            return null;
        }
        runningSubmission.setLocked(1);
        runningSubmission.setLockedUntil(getLockedUntil());
        runningSubmissionJPARepository.save(runningSubmission);
        RunningSubmission domain = runningSubmission.toDomain();
        domain.increaseVersion();
        return domain;
    }

    @Override
    public void delete(Id id) {
        runningSubmissionJPARepository.deleteById(id.toString());
    }


    @Override
    public void updateClaimed(RunningSubmission runningSubmission) {
        RunningSubmissionEntity entity = RunningSubmissionEntity.fromDomain(runningSubmission, 1, getLockedUntil());
        runningSubmissionJPARepository.save(entity);
        runningSubmission.increaseVersion();
    }

    @Override
    public RunningSubmission getById(Id id) {
        try {
            RunningSubmissionEntity entity = runningSubmissionJPARepository.getById(id.toString());
            if (entity == null) {
                return null;
            }
            return entity.toDomain();
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public RunningSubmission getByIdAndCoderId(Id id, Id coderId, String serviceToCreate) {
        try {
            RunningSubmissionEntity entity = runningSubmissionJPARepository
                    .findByIdAndCoderIdAndServiceToCreate(id.toString(), coderId.toString(), serviceToCreate);
            if (entity == null) {
                return null;
            }
            return entity.toDomain();
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public List<RunningSubmission> getByCoderId(int page, int perPage, Id coderId, String serviceToCreate) {
        Pageable pageable = PageRequest.of(page, perPage);
        Page<RunningSubmissionEntity> runningSubmissions = runningSubmissionJPARepository
                .findByCoderIdAndServiceToCreate(coderId.toString(), serviceToCreate, pageable);
        return runningSubmissions
                .stream()
                .map(RunningSubmissionEntity::toDomain)
                .collect(Collectors.toList());
    }
}
