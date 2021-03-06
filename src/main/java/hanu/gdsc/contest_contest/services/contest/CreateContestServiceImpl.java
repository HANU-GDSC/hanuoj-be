package hanu.gdsc.contest_contest.services.contest;

import hanu.gdsc.contest_contest.domains.Contest;
import hanu.gdsc.contest_contest.repositories.contest.ContestRepository;
import hanu.gdsc.share.domains.Id;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateContestServiceImpl implements CreateContestService {
    private final ContestRepository contestRepository;

    @Override
    public Id create(Input input) {
        Contest contest = Contest.create(
                input.name,
                input.description,
                input.startAt,
                input.endAt,
                input.createdBy
        );
        contestRepository.create(contest);
        return contest.getId();
    }
}
