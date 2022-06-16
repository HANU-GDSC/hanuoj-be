package hanu.gdsc.practiceProblemSubdomain.problemContext.services.problem;

import hanu.gdsc.coreSubdomain.problemContext.services.submit.SubmitService;
import hanu.gdsc.practiceProblemSubdomain.problemContext.domains.Problem;
import hanu.gdsc.practiceProblemSubdomain.problemContext.repositories.problem.ProblemRepository;
import hanu.gdsc.practiceProblemSubdomain.problemContext.services.core.problem.problem.SubmitCoreProblemProblemService;
import hanu.gdsc.share.error.NotFoundError;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubmitProblemServiceImpl implements SubmitProblemService {
    private ProblemRepository problemRepository;

    private SubmitCoreProblemProblemService submitCoreProblemService;

    @Override
    public SubmitService.Output submit(Input input) {
        Problem problem = problemRepository.getById(input.problemId);
        if (problem == null) {
            throw new NotFoundError("Problem not found");
        }
        return submitCoreProblemService.submit(SubmitCoreProblemProblemService.Input.builder()
                .coderId(input.coderId)
                .problemId(problem.getCoreProblemProblemId())
                .code(input.code)
                .programmingLanguage(input.programmingLanguage)
                .build());
    }
}