package hanu.gdsc.coreProblem.services.submissionEvent;

import hanu.gdsc.coreProblem.domains.Status;
import hanu.gdsc.coreProblem.domains.SubmissionCount;
import hanu.gdsc.coreProblem.domains.SubmissionEvent;
import hanu.gdsc.coreProblem.repositories.SubmissionCountRepository;
import hanu.gdsc.coreProblem.repositories.SubmissionEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProcessSubmissionEventService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessSubmissionEventService.class);

    @Autowired
    private SubmissionEventRepository submissionEventRepository;

    @Autowired
    private SubmissionCountRepository submissionCountRepository;

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void process() {
        try {
            SubmissionEvent submissionEvent = submissionEventRepository.getSubmissionEvent();
            if (submissionEvent == null) {
                return;
            }
            SubmissionCount submissionCount = submissionCountRepository.getByProblemId(submissionEvent.getProblemId());
            if (submissionEvent.getStatus().equals(Status.AC)) {
                submissionCount.increaseACsCount();
            }
            submissionCount.increaseSubmissionsCount();
            submissionCountRepository.update(submissionCount);
            submissionEventRepository.delete(submissionEvent.getId());
            LOGGER.info("Increased submission count for problemId: " + submissionEvent.getProblemId());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Process submission event error: " + e);

        }
    }
}