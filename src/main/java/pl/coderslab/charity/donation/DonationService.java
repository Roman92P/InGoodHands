package pl.coderslab.charity.donation;

import pl.coderslab.charity.model.Donation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface DonationService {
    Optional<Donation> getDonation(Long id);
    void addDonation(Donation donation);
    List<Donation> getAllDonations();
    void updateDonation(Donation donation);
    void removeDonation(Donation donation);
    Optional<Integer> getSumOfBags();
    Optional<Integer> getSumOfDonations();
    List<Donation>usersDonations(Long id);
    List<Donation>usersNotCollected(LocalDate day,LocalDate day1, LocalTime time);
    List<Donation>usersCollected(LocalDate day,LocalDate day1, LocalTime time);
}
