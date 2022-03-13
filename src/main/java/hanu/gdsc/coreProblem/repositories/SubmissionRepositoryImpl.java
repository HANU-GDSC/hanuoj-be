package hanu.gdsc.coreProblem.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hanu.gdsc.coreProblem.domains.Submission;
import hanu.gdsc.coreProblem.repositories.JPA.SubmissionJPARepository;
import hanu.gdsc.coreProblem.repositories.entities.SubmissionEntity;
import hanu.gdsc.share.domains.Id;

@Repository
public class SubmissionRepositoryImpl implements SubmissionRepository {
    @Autowired
    private SubmissionJPARepository submissionJPARepository;

    @Override
    public void create(Submission submission) {
        submissionJPARepository.save(SubmissionEntity.toEntity(submission));
    }

    @Override
    public List<Submission> getAllByProblemId(Id problemId) {
        return submissionJPARepository.getAllByProblemId(problemId.toUUID()).stream()  
                        .map(submissionEntity -> SubmissionEntity.toDomain(submissionEntity))
                        .collect(Collectors.toList());
    }
}
