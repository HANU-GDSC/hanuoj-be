package hanu.gdsc.contest_contest.services.contest;

import hanu.gdsc.contest_contest.domains.Contest;
import hanu.gdsc.contest_contest.domains.Problem;
import hanu.gdsc.contest_contest.repositories.contest.ContestRepository;
import hanu.gdsc.core_problem.services.problem.CreateProblemService;
import hanu.gdsc.core_problem.services.problem.SearchProblemService;
import hanu.gdsc.practiceProblem_problem.config.ServiceName;
import hanu.gdsc.share.domains.Id;
import hanu.gdsc.share.error.NotFoundError;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "Contest.CreateProblemService")
@AllArgsConstructor
public class AddProblemServiceImpl implements AddProblemService {
    private final SearchProblemService searchProblemService;
    private final ContestRepository contestRepository;

    private final CreateProblemService createCoreProblemService;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void execute(Input input) {
        Contest contest = contestRepository.getById(input.contestId);
        if (contest == null) {
            throw new NotFoundError("Unknown contest");
        }
        Id coreProblemId = createCoreProblemService
                .execute(new CreateProblemService.Input(
                        input.createCoreProblemInput.name,
                        input.createCoreProblemInput.description,
                        input.createCoreProblemInput.author,
                        input.createCoreProblemInput.createMemoryLimitInputs,
                        input.createCoreProblemInput.createTimeLimitInputs,
                        input.createCoreProblemInput.allowedProgrammingLanguages,
                        ServiceName.serviceName
                ));
        Problem problem = Problem.create(input.ordinal, coreProblemId, input.score);
        contest.addProblem(problem);
        contestRepository.update(contest);
    }

}
