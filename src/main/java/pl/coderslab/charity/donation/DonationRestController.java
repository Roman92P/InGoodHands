package pl.coderslab.charity.donation;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.charity.model.Donation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/userrest")
public class DonationRestController {

    private final DonationService donationService;

    public DonationRestController(DonationService donationService) {
        this.donationService = donationService;
    }

    @RequestMapping("/collected/{id}")
    public String getUserCollectedDonations(@PathVariable Long id) {
        List<Object[]> allUserCollectedDonations = donationService.findAllUserCollectedDonations(LocalDateTime.now(), id);
        List<DonationDTO> resultList = new ArrayList<>();
        for ( Object[] o : allUserCollectedDonations ) {
            DonationDTO donationDTO = new DonationDTO();
            donationDTO.setId((Long) o[0]);
            donationDTO.setQuantity((Integer) o[1]);
            donationDTO.setUserName((String)o[2]);
            donationDTO.setInstitutionName((String)o[3]);
            donationDTO.setStreet((String)o[4]);
            donationDTO.setCity((String)o[5]);
            donationDTO.setZipCode((String)o[6]);
            LocalDate o1 =(LocalDate) o[7];
            String s = o1.toString();
            donationDTO.setPickUpDate(s);
            LocalTime o2 =(LocalTime) o[8];
            String s1 = o2.toString();
            donationDTO.setPickUpTime(s1);
            donationDTO.setPickUpComment((String)o[9]);
            donationDTO.setPhoneNumber((String)o[10]);
            LocalDate o3 =(LocalDate) o[11];
            String s2 = o3.toString();
            donationDTO.setCreatedOn(s2);
            donationDTO.setUserId((Long)o[12]);
            resultList.add(donationDTO);
        }
        Gson gson = new Gson();
        return gson.toJson(resultList);
    }

    @RequestMapping("/notCollected/{id}")
    public String getUserNotCollectedDonations(@PathVariable Long id) {
        List<Object[]> allUserNotCollectedDonations = donationService.findAllUserNotCollectedDonations(LocalDateTime.now(), id);
        List<DonationDTO> resultList = new ArrayList<>();
        for ( Object[] o : allUserNotCollectedDonations ) {
            DonationDTO donationDTO = new DonationDTO();
            donationDTO.setId((Long) o[0]);
            donationDTO.setQuantity((Integer) o[1]);
            donationDTO.setUserName((String)o[2]);
            donationDTO.setInstitutionName((String)o[3]);
            donationDTO.setStreet((String)o[4]);
            donationDTO.setCity((String)o[5]);
            donationDTO.setZipCode((String)o[6]);
            LocalDate o1 =(LocalDate) o[7];
            String s = o1.toString();
            donationDTO.setPickUpDate(s);
            LocalTime o2 =(LocalTime) o[8];
            String s1 = o2.toString();
            donationDTO.setPickUpTime(s1);
            donationDTO.setPickUpComment((String)o[9]);
            donationDTO.setPhoneNumber((String)o[10]);
            LocalDate o3 =(LocalDate) o[11];
            String s2 = o3.toString();
            donationDTO.setCreatedOn(s2);
            donationDTO.setUserId((Long)o[12]);
            resultList.add(donationDTO);
        }
        Gson gson = new Gson();
        return gson.toJson(resultList);
    }
}
