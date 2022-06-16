package hanu.gdsc.coreSubdomain.problemContext.repositories.problem;

import java.util.List;

import hanu.gdsc.coreSubdomain.problemContext.domains.Problem;
import hanu.gdsc.share.domains.Id;

public interface ProblemRepository {
    public Problem getById(Id id, String serviceToCreate);

    public void create(Problem problem);

    public void update(Problem problem);

    public List<Problem> getByIds(List<Id> ids, String serviceToCreate);

    public void deleteById(Id id);
}