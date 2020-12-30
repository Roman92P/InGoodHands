package pl.coderslab.charity.donation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Component
public class DonationDTO {
//0
    private Long id;
//    1
    private int quantity;
//    2
    private String userName;
//    3
    private String institutionName;
//    4
    private String street;
//    5
    private String city;
//    6
    private String zipCode;
//    7
    private String pickUpDate;
//    8
    private String pickUpTime;
//    9
    private String pickUpComment;
//    10
    private String phoneNumber;
//    11
    private String createdOn;
//    12
    private Long userId;

    public DonationDTO() {
    }
}
