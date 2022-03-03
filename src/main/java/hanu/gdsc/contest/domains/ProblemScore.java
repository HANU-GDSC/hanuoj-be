package hanu.gdsc.contest.domains;

import hanu.gdsc.share.domains.Id;
import hanu.gdsc.share.domains.IdentitifedDomainObject;

public class ProblemScore extends IdentitifedDomainObject {
    private Id problemId;
    private int score;
    private int tryCount;

    public ProblemScore(Id id, long version) {
        super(id, version);
    }
}