package hanu.gdsc.coderAuth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import hanu.gdsc.coderAuth.services.SignUpService;
import hanu.gdsc.share.controller.ResponseBody;
import hanu.gdsc.share.error.BusinessLogicError;

@Controller
public class SignUpController {
    @Autowired
    private SignUpService signUpService;

    public static class Input {
        public String email;
        public String username;
        public String password;
    }

    @PostMapping("/coderAuth/signUp")
    public ResponseEntity<?> signUp(@RequestBody Input input) {
        try {
            signUpService.signUpService(input.email, input.username, input.password);
            return new ResponseEntity<>(
                    new ResponseBody("Success"), HttpStatus.OK);
        } catch (Throwable e) {
            if(e instanceof BusinessLogicError) {
                e.printStackTrace();
                return new ResponseEntity<>(new ResponseBody(e.getMessage(), ((BusinessLogicError) e).getCode(), null), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(new ResponseBody(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }
}
