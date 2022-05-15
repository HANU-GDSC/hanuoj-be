package hanu.gdsc.coderAuth.errors;

import hanu.gdsc.share.error.BusinessLogicError;

public class ExpiredToken extends BusinessLogicError{

    public ExpiredToken() {
        super("Token is expired", "EXPIRED_TOKEN");
    }
}
