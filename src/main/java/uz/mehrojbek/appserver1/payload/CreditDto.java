package uz.mehrojbek.appserver1.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreditDto {
    @NotNull
    private String series;

    @NotNull
    private String number;

    @NotNull
    private Double monthlySalary;

    @NotNull
    private Double creditAmount;

    @NotNull
    private Double creditPercent;
}
