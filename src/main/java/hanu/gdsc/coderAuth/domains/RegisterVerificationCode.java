package hanu.gdsc.coderAuth.domains;

import java.util.Random;

import hanu.gdsc.coderAuth.errors.ExpiredCode;
import hanu.gdsc.share.domains.DateTime;
import hanu.gdsc.share.domains.Id;

public class RegisterVerificationCode {
    private Id coderId;
    private String code;
    private DateTime expireAt;
    
    private RegisterVerificationCode(Id coderId, String code, DateTime expireAt) {
        this.coderId = coderId;
        this.code = code;
        this.expireAt = expireAt;
    }

    public static RegisterVerificationCode createRegisterVerificationCode(Id coderId) {
        return new RegisterVerificationCode(
            coderId, 
            generateRandomCode(), 
            DateTime.now().plusMinutes(60));
    }
    
    public Id getCoderId() {
        return coderId;
    }

    public String getCode() {
        return code;
    }

    public DateTime getExpireAt() {
        return expireAt;
    }

    private static String generateRandomCode() {
        Random rand = new Random();
        int randomCode = rand.nextInt(900000)+100000;
        return String.valueOf(randomCode);
    }

    public boolean invalidate() {
        DateTime time = DateTime.now();
        if(!time.isBefore(expireAt)) {
            throw new ExpiredCode("Register verification code is expired");
        }
        return false;
    }
}
