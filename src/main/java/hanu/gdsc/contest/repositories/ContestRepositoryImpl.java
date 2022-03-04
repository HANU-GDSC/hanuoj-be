package hanu.gdsc.contest.repositories;

import hanu.gdsc.contest.domains.Contest;
import hanu.gdsc.contest.repositories.JPA.ContestJPARepository;
import hanu.gdsc.contest.repositories.entities.ContestEntity;
import hanu.gdsc.share.domains.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ContestRepositoryImpl implements ContestRepository {
    @Autowired
    private ContestJPARepository contestJPARepository;

    @Override
    public void create(Contest contest) {
        contestJPARepository.save(ContestEntity.fromDomain(contest));
    }

    @Override
    public void update(Contest contest) {
        contestJPARepository.save(ContestEntity.fromDomain(contest));
        contest.increaseVersion();
    }

    @Override
    public Contest getById(Id id) {
        Optional<ContestEntity> entity = contestJPARepository.findById(id.toUUID());
        if (entity.isEmpty()) {
            return null;
        }
        return entity.get().toDomain();
    }

    @Override
    public List<Contest> search(int skip, int limit) {
        return null;
    }
}
