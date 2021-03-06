package hanu.gdsc.coderAuth.repositories.session;

import hanu.gdsc.coderAuth.domains.Session;
import hanu.gdsc.share.domains.Id;

public interface SessionRepository {
    public void save(Session session);

    public Session getById(Id id);

    public Session getByCoderId(Id coderId);

    public void deleteById(Id id);

    public void deleteSession(Id coderId, Id sessionId);
}
