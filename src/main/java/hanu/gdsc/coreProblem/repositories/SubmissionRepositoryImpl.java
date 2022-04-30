package hanu.gdsc.coreProblem.repositories;

import hanu.gdsc.coreProblem.domains.Submission;
import hanu.gdsc.coreProblem.repositories.JPA.SubmissionJPARepository;
import hanu.gdsc.coreProblem.repositories.entities.SubmissionEntity;
import hanu.gdsc.share.domains.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SubmissionRepositoryImpl implements SubmissionRepository {
    @Autowired
    private SubmissionJPARepository submissionJPARepository;

    @Override
    public void create(Submission submission) {
        submissionJPARepository.save(SubmissionEntity.toEntity(submission));
    }

    @Override
    public List<Submission> get(int page, int perPage, Id problemId, Id coderId, String serviceToCreate) {
        Pageable pageable = PageRequest.of(page, perPage);
        Page<SubmissionEntity> submissionsEntity;
        if (problemId != null && coderId != null) {
            submissionsEntity = submissionJPARepository.findByProblemIdAndCoderIdAndServiceToCreate(problemId.toString(),
                    coderId.toString(),
                    serviceToCreate,
                    pageable);
        } else if (problemId != null) {
            submissionsEntity = submissionJPARepository.findByProblemIdAndServiceToCreate(problemId.toString(),
                    serviceToCreate,
                    pageable);
        } else if (coderId != null) {
            submissionsEntity = submissionJPARepository.findByCoderIdAndServiceToCreate(coderId.toString(),
                    serviceToCreate,
                    pageable);
        } else {
            submissionsEntity = submissionJPARepository.findByServiceToCreate(serviceToCreate, pageable);
        }
        return submissionsEntity.getContent().stream()
                .map(s -> SubmissionEntity.toDomain(s))
                .collect(Collectors.toList());
    }

    @Override
    public Submission getById(Id id, String serviceToCreate) {
        try {
            SubmissionEntity submissionEntity = submissionJPARepository.getByIdAndServiceToCreate(id.toString(), serviceToCreate);
            return SubmissionEntity.toDomain(submissionEntity);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

}
