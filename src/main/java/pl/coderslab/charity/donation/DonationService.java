package pl.coderslab.charity.donation;

import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.User;

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
    List<Donation>usersNotCollected( Long id,LocalDate day,LocalDate day1, LocalTime time);
    List<Donation>usersCollected( Long id, LocalDate day,LocalDate day1, LocalTime time);

    List<Object[]>findAllUserCollectedDonations(LocalDateTime nowDateTime, Long userId);
    List<Object[]>findAllUserNotCollectedDonations(LocalDateTime nowDateTime, Long userId);

    List<Donation>getAlreadyCollectedDonations(LocalDateTime currentDate, User user);
    List<Donation>getNotCollectedYetDonations(LocalDateTime currentDate, User user);
}
