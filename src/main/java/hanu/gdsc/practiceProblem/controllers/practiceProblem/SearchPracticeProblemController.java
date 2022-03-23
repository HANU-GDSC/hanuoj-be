package hanu.gdsc.practiceProblem.controllers.practiceProblem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hanu.gdsc.practiceProblem.services.practiceProblem.SearchPracticeProblemService;
import hanu.gdsc.share.controller.ResponseBody;
import hanu.gdsc.share.domains.Id;
import hanu.gdsc.share.error.BusinessLogicError;


@RestController
public class SearchPracticeProblemController {
    @Autowired
    private SearchPracticeProblemService servicePracticeProblemService;

    @GetMapping("/practiceProblem/practiceProblemDetail")
    public ResponseEntity<?> getById(@RequestParam String id){
        try {
            SearchPracticeProblemService.Output output = servicePracticeProblemService.getById(new Id(id));
            return new ResponseEntity<>(
                new ResponseBody("Tìm kiếm bài toán thành công", output), HttpStatus.OK
            );
        } catch (Throwable e) {
            if(e.getClass().equals(BusinessLogicError.class)) {
                e.printStackTrace();
                return new ResponseEntity<>(new ResponseBody(e.getMessage(), ((BusinessLogicError) e).getCode()), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(new ResponseBody(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }
}
