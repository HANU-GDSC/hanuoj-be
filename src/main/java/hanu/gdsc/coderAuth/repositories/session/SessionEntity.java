package hanu.gdsc.coderAuth.repositories.session;

import java.lang.reflect.Constructor;

import javax.persistence.*;

import hanu.gdsc.coderAuth.domains.Session;
import lombok.*;

@Entity
@Table(name = "coder_auth_session")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionEntity {
    @Id
    @Column(columnDefinition = "VARCHAR(30)")
    private String id;
    @Column(columnDefinition = "VARCHAR(30)")
    private String coderId;
    private String expireAt;    

    public static SessionEntity toEntity(Session session) {
        return SessionEntity.builder()
        .id(session.getId().toString())
        .coderId(session.getCoderId().toString())
        .expireAt(session.getExpireAt().toZonedDateTime().toString())
        .build();
    }

    public Session toDomain() {
        try {
            Constructor<Session> con = Session.class.getDeclaredConstructor(
                hanu.gdsc.share.domains.Id.class,
                hanu.gdsc.share.domains.Id.class,
                hanu.gdsc.share.domains.DateTime.class
            );
            con.setAccessible(true);
            return con.newInstance(
                new hanu.gdsc.share.domains.Id(id),
                new hanu.gdsc.share.domains.Id(coderId),
                new hanu.gdsc.share.domains.DateTime(expireAt)
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }
}
