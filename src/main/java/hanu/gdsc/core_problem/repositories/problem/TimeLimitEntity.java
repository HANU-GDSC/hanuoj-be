package hanu.gdsc.core_problem.repositories.problem;

import java.lang.reflect.Constructor;

import javax.persistence.*;

import hanu.gdsc.core_problem.domains.Millisecond;
import hanu.gdsc.core_problem.domains.ProgrammingLanguage;
import hanu.gdsc.core_problem.domains.TimeLimit;
import lombok.*;

@Entity
@Table(name="core_problem_time_limit")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class TimeLimitEntity {
    @Id
    @Column(columnDefinition = "VARCHAR(30)")
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="problem_id", columnDefinition = "VARCHAR(30)")
    private ProblemEntity problem;
    private String programmingLanguage;
    private long timeLimit;

    public static TimeLimitEntity toEntity(TimeLimit timeLimitDomain) {
        return TimeLimitEntity.builder()
                .id(timeLimitDomain.getId().toString())
                .programmingLanguage(timeLimitDomain.getProgrammingLanguage().toString())
                .timeLimit(timeLimitDomain.getTimeLimit().getValue())
                .build();
    }

    public static TimeLimit toDomain(TimeLimitEntity timeLimitEntity) {
        try {
            Constructor<TimeLimit> constructor = TimeLimit.class.getDeclaredConstructor(
                hanu.gdsc.share.domains.Id.class,
                ProgrammingLanguage.class,
                Millisecond.class
            );
            constructor.setAccessible(true);
            return constructor.newInstance(
                new hanu.gdsc.share.domains.Id(timeLimitEntity.getId()),
                ProgrammingLanguage.valueOf(timeLimitEntity.getProgrammingLanguage()),
                new Millisecond(timeLimitEntity.getTimeLimit())
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }
}
