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
public class DonationDTO {

    private Long id;
    private int quantity;
    private String userName;
    private String institutionName;
    private String street;
    private String city;
    private String zipCode;
    private String pickUpDate;
    private String pickUpTime;
    private String pickUpComment;
    private String phoneNumber;
    private String createdOn;
    private Long userId;
    public DonationDTO() {
    }
}
