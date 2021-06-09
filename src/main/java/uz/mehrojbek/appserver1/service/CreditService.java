package uz.mehrojbek.appserver1.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.Exported;
import jdk.internal.org.objectweb.asm.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.mehrojbek.appserver1.entity.CreditRequest;
import uz.mehrojbek.appserver1.payload.ApiResponse;
import uz.mehrojbek.appserver1.payload.CreditDto;
import uz.mehrojbek.appserver1.payload.Person;
import uz.mehrojbek.appserver1.repository.CreditRequestRepository;
import uz.mehrojbek.appserver1.utils.SystemUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class CreditService {
    @Autowired
    CreditRequestRepository creditRequestRepository;

    public HttpHeaders setHeader(){
        String auth = SystemUtils.USERNAME + ":" + SystemUtils.PASSWORD;
        byte[] encodedAuth = Base64.getEncoder().encode(
                auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authHeader);
        return headers;
    }


    public ApiResponse calculateCredit(CreditDto creditDto) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8081/api/person?series=" + creditDto.getSeries() + "&number=" + creditDto.getNumber();

        HttpHeaders headers = setHeader();

        try {
            ResponseEntity<ApiResponse> apiResponseResponseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), ApiResponse.class);

            if (apiResponseResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
                ApiResponse apiResponse = apiResponseResponseEntity.getBody();
                assert apiResponse != null;
                ObjectMapper mapper = new ObjectMapper();

                Person person = mapper.convertValue(apiResponse.getObject(), Person.class);

                Double monthlySalary = creditDto.getMonthlySalary();
                double yearlySalary = monthlySalary * 12 * 0.7;
                Double creditAmount = creditDto.getCreditAmount();
                double creditAmountWithPercent = creditAmount * (1 + creditDto.getCreditPercent() / 100);

                if (yearlySalary > creditAmountWithPercent) {

                    CreditRequest creditRequest = new CreditRequest();

                    creditRequest.setCreditAmount(creditDto.getCreditAmount());
                    creditRequest.setCreditPercent(creditDto.getCreditPercent());
                    creditRequest.setPersonId(person.getId());
                    creditRequestRepository.save(creditRequest);
                    return new ApiResponse("Kredit uchun so'rov qabul qilindi", true);
                }

                double maxCreditAmount = yearlySalary / (1 + creditDto.getCreditPercent() / 100);
                return new ApiResponse("Siz yillik " + creditDto.getCreditPercent() + " foiz bilan maksimum " + maxCreditAmount +
                        " miqdorida kredit olishingiz mumkin", false);
            }

            return new ApiResponse("passport ma'lumotlari topilmadi", false);
        } catch (Exception e) {
            return new ApiResponse("passport ma'lumotlari topilmadi", false);
        }
    }


}



