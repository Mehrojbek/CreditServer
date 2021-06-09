package uz.mehrojbek.appserver1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.mehrojbek.appserver1.payload.ApiResponse;
import uz.mehrojbek.appserver1.payload.CreditDto;
import uz.mehrojbek.appserver1.service.CreditService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/credit")
public class CreditController {

    @Autowired
    CreditService creditService;

    @PostMapping
    public HttpEntity<?> calculateCredit(@Valid @RequestBody CreditDto creditDto){
        ApiResponse apiResponse = creditService.calculateCredit(creditDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

}
