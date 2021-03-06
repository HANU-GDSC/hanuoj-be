package hanu.gdsc.coder.repositories;

import hanu.gdsc.coder.domains.Coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CoderRepositoryImpl implements CoderRepository {
    @Autowired
    private CoderJpaRepository coderJpaRepository;

    @Override
    public void create(Coder coder) {
        coderJpaRepository.save(CoderEntity.fromDomains(coder));
    }
}
