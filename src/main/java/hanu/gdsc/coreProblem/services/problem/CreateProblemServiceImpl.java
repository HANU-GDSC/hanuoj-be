package hanu.gdsc.coreProblem.services.problem;

import hanu.gdsc.coreProblem.domains.Problem;
import hanu.gdsc.coreProblem.repositories.ProblemRepository;
import hanu.gdsc.coreProblem.services.submissionsCount.CreateSubmissionCountService;
import hanu.gdsc.share.domains.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateProblemServiceImpl implements CreateProblemService {
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private CreateSubmissionCountService createSubmissionCountService; 

    @Override
    public Id execute(Input input) {
        Problem problem = Problem.create(
                input.name,
                input.description,
                input.author,
                input.createTestCaseInputs,
                input.createMemoryLimitInputs,
                input.createTimeLimitInputs,
                input.allowedProgrammingLanguages,
                input.serviceToCreate
        );
        problemRepository.create(problem);
        createSubmissionCountService.create(problem.getId());
        return problem.getId();
    }
}
