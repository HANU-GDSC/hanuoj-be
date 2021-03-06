package hanu.gdsc.core_problem.repositories.testCase;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import hanu.gdsc.core_problem.domains.TestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hanu.gdsc.share.domains.Id;

@Repository
public class TestCaseRepositoryImpl implements TestCaseRepository{
    @Autowired
    private TestCaseJPARepository testCaseJpaRepository;
    
    @Override
    public List<TestCase> getByProblemId(Id problemId, String serviceToCreate) {
        List<TestCaseEntity> testCasesEntity = testCaseJpaRepository.getByProblemIdAndServiceToCreate(problemId.toString(), serviceToCreate);
        return testCasesEntity.stream()
                .map(t -> TestCaseEntity.toDomain(t))
                .collect(Collectors.toList());
    }

    @Override
    public void create(TestCase testCase) {
        testCaseJpaRepository.save(TestCaseEntity.toEntity(testCase));
    }

    @Override
    public List<TestCase> getSampleTestCases(Id problemId, String serviceToCreate) {
        List<TestCaseEntity> sampleTestCasesEntity = testCaseJpaRepository.getSampleTestCases(problemId.toString(), serviceToCreate);
        return sampleTestCasesEntity.stream()
            .map(t -> TestCaseEntity.toDomain(t))
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Id id, String serviceToDelete) {
        testCaseJpaRepository.deleteByIdAndServiceToCreate(
            id.toString(),
            serviceToDelete
        );
        
    }

    @Override
    public TestCase getByProblemIdAndOrdinal(Id problemId, int ordinal, String serviceToCreate) {
        return TestCaseEntity.toDomain(
            testCaseJpaRepository.getByProblemIdAndOrdinalAndServiceToCreate(
                problemId.toString(),
                ordinal,
                serviceToCreate)
            );
    }

    @Override
    @Transactional
    public void update(TestCase testCase) {
        testCaseJpaRepository.save(TestCaseEntity.toEntity(testCase));  
    }    
}
