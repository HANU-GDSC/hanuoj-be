package hanu.gdsc.coderAuth.domains;

import hanu.gdsc.share.domains.DateTime;
import hanu.gdsc.share.domains.Id;
import hanu.gdsc.share.domains.IdentifiedDomainObject;
import hanu.gdsc.share.error.BusinessLogicError;

public class Session extends IdentifiedDomainObject{
    private Id coderId;
    private DateTime expireAt; 

    private Session(Id id, Id coderId, DateTime expireAt) {
        super(id);
        this.coderId = coderId;
        this.expireAt = expireAt;
    }

    public static Session createSession(Id id, Id coderId) {
        return new Session(
            id, 
            coderId, 
            DateTime.now().plusMinutes(100));
    }

    public Id getCoderId() {
        return coderId;
    }

    public DateTime getExpireAt() {
        return expireAt;
    }

    public boolean invalidate() {
        DateTime time = DateTime.now();
        if(!time.isBefore(expireAt)) {
            throw new BusinessLogicError("Session is expired", "EXPIRED_SESSION");
        }
        return false;
    }
}
