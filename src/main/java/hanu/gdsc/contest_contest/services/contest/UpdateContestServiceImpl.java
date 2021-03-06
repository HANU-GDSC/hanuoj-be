package hanu.gdsc.contest_contest.services.contest;

import hanu.gdsc.contest_contest.domains.Contest;
import hanu.gdsc.contest_contest.repositories.contest.ContestRepository;
import hanu.gdsc.share.error.BusinessLogicError;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateContestServiceImpl implements UpdateContestService {
    private final ContestRepository contestRepository;

    @Override
    public void execute(Input input) {
        Contest contest = contestRepository.getById(input.contestId);
        if (contest == null) {
            throw new BusinessLogicError("Contest không tồn tại.", "NOT_FOUND");
        }
        if (input.name != null) {
            contest.setName(input.name);
        }
        if (input.description != null) {
            contest.setDescription(input.name);
        }
        if (input.startAt != null && input.endAt != null) {
            contest.setStartAtAndEndAt(input.startAt, input.endAt);
        }
        contestRepository.update(contest);
    }
}
