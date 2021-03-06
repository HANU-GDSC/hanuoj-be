package hanu.gdsc.practiceProblem_problem.services.problem;

import hanu.gdsc.core_problem.domains.MemoryLimit;
import hanu.gdsc.core_problem.domains.ProgrammingLanguage;
import hanu.gdsc.core_problem.domains.TimeLimit;
import hanu.gdsc.practiceProblem_problem.config.ServiceName;
import hanu.gdsc.practiceProblem_problem.domains.Difficulty;
import hanu.gdsc.practiceProblem_problem.domains.Problem;
import hanu.gdsc.practiceProblem_problem.repositories.problem.ProblemRepository;
import hanu.gdsc.share.domains.Id;
import hanu.gdsc.share.error.BusinessLogicError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "PracticeProblem.UpdateProblemServiceImpl")
@AllArgsConstructor
public class UpdateProblemService {
    private final ProblemRepository problemRepository;
    private final hanu.gdsc.core_problem.services.problem.UpdateProblemService updateCoreProblemService;

    @AllArgsConstructor
    @Getter
    public static class Input {
        public Id problemId;
        public Difficulty difficulty;
        public String name;
        public String description;
        public List<MemoryLimit.CreateInput> memoryLimits;
        public List<TimeLimit.CreateInput> timeLimits;
        public List<ProgrammingLanguage> allowedProgrammingLanguages;
    }

    public void update(Input input) {
        Problem problem = problemRepository.getById(input.problemId);
        if (problem == null) {
            throw new BusinessLogicError("problem doesn't exist.", "NOT_FOUND");
        }
        if (input.difficulty != null) {
            problem.setDifficulty(input.difficulty);
        }
        problemRepository.update(problem);
        updateCoreProblemService.update(new hanu.gdsc.core_problem.services.problem.UpdateProblemService.Input(
                problem.getCoreProblemProblemId(),
                ServiceName.serviceName,
                input.name,
                input.description,
                input.memoryLimits,
                input.timeLimits,
                input.allowedProgrammingLanguages
        ));
    }
}
