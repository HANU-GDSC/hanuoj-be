package hanu.gdsc.coreProblem.services.problem;

import hanu.gdsc.coreProblem.domains.*;
import hanu.gdsc.coreProblem.repositories.ProblemRepository;
import hanu.gdsc.coreProblem.repositories.SubmissionRepository;
import hanu.gdsc.share.error.BusinessLogicError;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SubmitServiceImpl implements SubmitService {
    private final ProblemRepository problemRepository;
    private final RunCodeService runCodeService;
    private final SubmissionRepository submissionRepository;

    @Override
    public Output submit(Input input) {
        Output output = runTests(input);
        Submission submission = Submission.create(
                input.problemId,
                input.programmingLanguage,
                null,
                null,
                input.code,
                output.status,
                output.failedTestCase == null ?
                        null :
                        FailedTestCaseDetail.fromTestCase(
                                0,
                                output.actualOutput,
                                output.failedTestCase
                        ),
                input.serviceName
        );
        submissionRepository.create(submission);
        return output;
    }

    private Output runTests(Input input) {
        Problem problem = problemRepository.getById(input.problemId);
        if (!problem.getAllowedProgrammingLanguages().contains(input.programmingLanguage)) {
            throw new BusinessLogicError("Bài tập không hỗ trợ ngôn ngữ lập trình " + input.programmingLanguage, "LANGUAGE_NOT_SUPPORTED");
        }
        for (TestCase testCase : problem.getSortedByOrdinalTestCases()) {
            RunCodeService.Output runCodeServiceOutput = runCodeService.execute(input.code, testCase.getInput(), input.programmingLanguage);
            // Check time limit
            TimeLimit timeLimit = problem.getTimeLimitByProgrammingLanguage(input.programmingLanguage);
            if (runCodeServiceOutput.runTime.greaterThan(timeLimit.getTimeLimit())) {
                return Output.builder()
                        .runTime(runCodeServiceOutput.runTime)
                        .memory(runCodeServiceOutput.memory)
                        .status(Status.TLE)
                        .failedTestCase(null)
                        .build();
            }
            // Check memory limit
            MemoryLimit memoryLimit = problem.getMemoryLimitByProgrammingLanguage(input.programmingLanguage);
            if (runCodeServiceOutput.memory.greaterThan(memoryLimit.getMemoryLimit())) {
                return Output.builder()
                        .runTime(runCodeServiceOutput.runTime)
                        .memory(runCodeServiceOutput.memory)
                        .status(Status.MLE)
                        .failedTestCase(null)
                        .build();
            }
            // Check answer
            if (!runCodeServiceOutput.output.getOutput().equals(testCase.getExpectedOutput())) {
                return Output.builder()
                        .runTime(runCodeServiceOutput.runTime)
                        .memory(runCodeServiceOutput.memory)
                        .status(Status.WA)
                        .failedTestCase(testCase)
                        .build();
            }
        }
        return Output.builder()
                .memory(null) // TODO: calculate average run time & memory
                .runTime(null)
                .status(Status.AC)
                .build();
    }
}
