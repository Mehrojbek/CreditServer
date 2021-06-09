package uz.mehrojbek.appserver1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {

    private Integer id;

    private String firstName;

    private String lastname;

    private String patronymic;

    private String series;//PASSPORT SERIES

    private String number;//PASSPORT NUMBER
}
