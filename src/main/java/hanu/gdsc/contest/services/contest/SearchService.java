package hanu.gdsc.contest.services.contest;

import hanu.gdsc.contest.domains.Contest;
import hanu.gdsc.share.domains.Id;

import java.util.List;

public interface SearchService {
    public Contest getById(Id contestId);

    public List<Contest> search(int page, int perPage);
}